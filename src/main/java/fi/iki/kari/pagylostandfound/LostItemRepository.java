package fi.iki.kari.pagylostandfound;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fi.iki.kari.pagylostandfound.datamodel.LostItem;
import fi.iki.kari.pagylostandfound.datamodel.LostItem.types;

public interface LostItemRepository extends CrudRepository<LostItem, Long>{

	LostItem findById (Long id);
	LostItem findByIdAndCreatorFbId(Long id, String fbId);
	
	@Modifying
	@Query("update LostItem i set"
			+ " i.itemName = :name,"
			+ " i.itemDescr = :descr,"
			+ " i.lat = :lat,"
			+ " i.lng = :lng,"
			+ " i.fileName = :fname,"
			+ " i.enabled = :enabled,"
			+ " i.myType = :type,"
			+ " i.updated= :updated,"
			+ " i.contactDetail = :contact"
			+ " where ID = :id")
	void updateSelectiveById(
			@Param("name") String itemName, 
			@Param("descr") String itemDescr, 
			@Param("lat") float lat, 
			@Param("lng") float lng, 
			@Param("fname") String fileName, 
			@Param("enabled") boolean enabled, 
			@Param("type") types type,
			@Param("updated") LocalDateTime now, 
			@Param("contact") String contactDetail,
			@Param("id") Long id);
	
	List<LostItem> findFirst10ByEnabledIsTrueOrderByCreatedDesc();
		
}
