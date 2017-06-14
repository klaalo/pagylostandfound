package fi.iki.kari.pagylostandfound;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import fi.iki.kari.pagylostandfound.datamodel.LostItem;

@Service
public class ImageUrlService {
	
	Logger logger = Logger.getLogger(this.getClass().getName());

	public String getUrl(LostItem item) {
		if (item.getFileName() != null && !item.getFileName().isEmpty()) {
			
			Storage storage = StorageOptions.getDefaultInstance().getService();
			
			logger.debug("fileName: " + item.getFileName());
			
			BlobInfo bi = BlobInfo.newBuilder("pagy-lostitems-images", item.getFileName()).build();
			
			if (bi.getBlobId() != null) {
				try {
					Blob blob = storage.get(bi.getBlobId());
					logger.debug("blob name: " + bi.getName());
					
					if (blob.exists()) {
						logger.debug("blob exists, mediaLink: " + blob.getMediaLink());
						return blob.getMediaLink();
					} else {
						logger.debug("blob doesn't exist");
						return "";
					}
				} catch (Exception e) {
					logger.debug("exeption handler");
					return "";
				}
			}
		}
		return "";
	}
		
	
}
