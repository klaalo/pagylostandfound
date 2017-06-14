package fi.iki.kari.pagylostandfound.datamodel;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

@Entity
public class LostItem {
	
	@Transient
	private final Logger logger = Logger.getLogger(this.getClass()); 
	
	@Transient
	private static final PolicyFactory pf = new HtmlPolicyBuilder()
		.toFactory();
	@Transient
	private static final PolicyFactory lf = Sanitizers.LINKS;


	@Id 
	@GeneratedValue
	Long id;

	LocalDateTime created = LocalDateTime.now();
	LocalDateTime updated = LocalDateTime.now();
	String itemName;
	
	@Column(columnDefinition = "TEXT")
	String itemDescr;
	
	float lat;
	float lng;
	
	String fileName;
	
	String creatorFbId;
	String contactDetail;
	
	boolean enabled = true;
	
	types myType = types.Kadonnut;
	public enum types {
		Kadonnut, Löytynyt
	}
	
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String name) {
		this.itemName = pf.sanitize(name);
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String description) {
		this.itemDescr = pf.sanitize(description);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void stampCreated() {
		this.created = LocalDateTime.now();
	}
	public LocalDateTime getUpdated() {
		return this.updated;
	}
	public void stampUpdated() {
		this.updated = LocalDateTime.now();
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreatorFbId() {
		return creatorFbId;
	}
	public void setCreatorFbId(String creatorFbId) {
		this.creatorFbId = creatorFbId;
	}
	public String getContactDetail() {
		return contactDetail;
	}
	public void setContactDetail(String contactDetail) {
		this.contactDetail = lf.sanitize(contactDetail);
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean getType() {
		return this.myType == types.Kadonnut;
	}
	public types getTypeEnum() {
		return this.myType;
	}
	public void setType(boolean type) {
		logger.info("type: " + type);
		this.myType = type ? types.Kadonnut : types.Löytynyt; 
	}
		
}
