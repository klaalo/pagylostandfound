package fi.iki.kari.pagylostandfound;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import fi.iki.kari.pagylostandfound.datamodel.LostItem;

@Controller
public class DefaultController {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private LostItemRepository lostItems;
	
	@Autowired
	ImageUrlService urlService;

	@GetMapping("/")
	public String front(Model model) {
		model.addAttribute("itemList", lostItems.findFirst10ByEnabledIsTrueOrderByCreatedDesc());
		return "itemList";
	}
	
	@GetMapping("/item/{itemId}/view")
	public String itemView(@PathVariable Long itemId, Model model,
			Principal princ) {
		LostItem item = lostItems.findById(itemId);
		if (item.isEnabled() || 
				(princ != null && item.getCreatorFbId().equals(princ.getName()))) {
			model.addAttribute("item", item);
			return "itemView";
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/item/{id}/modify")
	public String itemModify(
			@PathVariable Long id,
			Model model,
			Principal princ
			) {
		LostItem item = null;
		if (id != null) {
			item = lostItems.findByIdAndCreatorFbId(id, princ.getName());
		}
		if (item == null) {
			item = new LostItem();
			item.setContactDetail("https://facebook.com/" + princ.getName());
			model.addAttribute("itemForm", item);
		} else {
			
			String url = urlService.getUrl(item);
			if (!url.isEmpty()) {
				model.addAttribute("imageUrl", url);
			}
			
			model.addAttribute("itemForm", item);
		}
		return "itemForm";
	}
	
	@Transactional
	@PostMapping("/item/{id}/modify")
	public String itemModify (
			@ModelAttribute("itemForm") LostItem item,
			Principal princ
			) {
		if (item.getId() == null) {
			logger.info("new item");
			item.setCreatorFbId(princ.getName());
			item = lostItems.save(item);
		} else {
			LostItem dbItem = lostItems.findById(item.getId());
			logger.debug("lat: " + item.getLat() + " lng: " + item.getLng());
			logger.debug("principal: " + princ.getName());
			logger.info("item creator: " + dbItem.getCreatorFbId());
			if (dbItem.getCreatorFbId().equals(princ.getName())) {
				lostItems.updateSelectiveById(item.getItemName(), item.getItemDescr(),
						item.getLat(), item.getLng(), item.getFileName(),
						item.isEnabled(), item.getTypeEnum(),
						LocalDateTime.now(),
						item.getContactDetail(),
						item.getId());
			}
			// TODO: Some kind of indication of illegal action?
		}
		return "redirect:/item/" + item.getId() + "/modify";
	}
	
	@SuppressWarnings("serial")
	@PostMapping("/imageUpload")
	@ResponseBody
	public Map<String, String> imageUpload (@RequestParam("file") MultipartFile file) {

		String fileName = file.getOriginalFilename();
		logger.info("fileName: " + fileName);
		logger.info("content-type: " + file.getContentType());
		int pointIdx = fileName.lastIndexOf(".");
		String postfix; 
		if (pointIdx == -1) {
			postfix = "";
		} else {
			postfix = fileName.substring(pointIdx);
		}
		
		String cloudFile = UUID.randomUUID().toString() + postfix;
		
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobInfo blobInfo = BlobInfo.newBuilder("pagy-lostitems-images", cloudFile)
				.setContentDisposition("inline")
				.setContentType(file.getContentType())
				.setMetadata(
						new HashMap<String, String>() {{
							put("origFileName", fileName);
							// TODO: add princ.getName() as metadata
						}})
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
				.build();
		try {
			storage.create(blobInfo, file.getInputStream());
			return new HashMap<String, String>() {{
				put("status", "ok");
				put("fileName", cloudFile);
			}};
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, String>() {{
				put("status", "error");
				put("error", e.getMessage());
			}};
		}
		
	}
	
	@GetMapping("/princ")
	@ResponseBody
	public Principal princ (Principal princ) {
		return princ;
	}
	
	@GetMapping("/auth")
	@ResponseBody
	public Authentication auth(Authentication auth) {
		return auth;
	}

}
