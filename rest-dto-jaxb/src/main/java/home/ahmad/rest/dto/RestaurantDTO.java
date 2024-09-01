package home.ahmad.rest.dto;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

import home.ahmad.model.PhoneContact;
import home.ahmad.model.Recommendation;
import home.ahmad.model.Restaurant;
import home.ahmad.model.TimesOfService;

/**
 * @author Ahmad Alrefai
 */
public class RestaurantDTO implements Serializable {

    private static final long serialVersionUID = 2615462980849602805L;
    
    private Long id;
    private NestedCategoryDTO category;
    private AddressDTO address;
    private String website;
    private String location;
    private String name;
    private Set<NestedPhoneContactDTO> phoneContact = new HashSet<>();
    private Set<NestedRecommendationDTO> recommendation = new HashSet<>();
    private String linkGooglemaps;
    private Set<NestedTimesOfServiceDTO> timesOfService = new HashSet<>();
    private String linkMenu;
    private String dressCode;

    public RestaurantDTO() {
    }

    public RestaurantDTO(final Restaurant entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.category = new NestedCategoryDTO(entity.getCategory());
            this.address = new AddressDTO(entity.getAddress());
            this.website = entity.getWebsite();
            this.location = entity.getLocation();
            this.name = entity.getName();
            this.linkGooglemaps = entity.getLinkGooglemaps();
            this.linkMenu = entity.getLinkMenu();
            this.dressCode = entity.getDressCode();
            populateSet(entity.getPhoneContact(), this.phoneContact, NestedPhoneContactDTO::new);
            populateSet(entity.getRecommendation(), this.recommendation, NestedRecommendationDTO::new);
            populateSet(entity.getTimesOfService(), this.timesOfService, NestedTimesOfServiceDTO::new);
        }
    }
    /**
     * wird verwendet, um die verschiedenen Sets (phoneContact, recommendation, timesOfService) im RestaurantDTO zu befüllen. 
     * Dies reduziert den sich wiederholenden Code beim Initialisieren der DTOs.
     * 
     * @param <E>
     * @param <D>
     * @param entitySet
     * @param dtoSet
     * @param converter
     */

    private <E, D> void populateSet(Set<E> entitySet, Set<D> dtoSet, Function<E, D> converter) {
        if (entitySet != null) {
            for (E entity : entitySet) {
                dtoSet.add(converter.apply(entity));
            }
        }
    }
    /**
     * synchronisiert die Sets von Entitäten und DTOs, indem sie zuerst alle Entitäten entfernt, die nicht mehr in den DTOs vorhanden sind, 
     * und dann neue Entitäten hinzufügt, die in den DTOs enthalten sind, aber noch nicht in der Entität.
     * 
     * @param <E>
     * @param <D>
     * @param entitySet
     * @param dtoSet
     * @param em
     * @param entityClass
     * @param getIdFunction
     */
    private <E, D> void syncEntitySet(Set<E> entitySet, Set<D> dtoSet, EntityManager em, Class<E> entityClass, Function<D, Long> getIdFunction) {
        Iterator<E> entityIterator = entitySet.iterator();
        while (entityIterator.hasNext()) {
            E entity = entityIterator.next();
            Long entityId = getIdFunction.apply(dtoSet.stream().filter(dto -> getIdFunction.apply(dto).equals(getIdFunction.apply((D) entity))).findFirst().orElse(null));
            if (entityId == null) {
                entityIterator.remove();
            }
        }

        for (D dto : dtoSet) {
            Long dtoId = getIdFunction.apply(dto);
            if (dtoId != null && entitySet.stream().noneMatch(e -> getIdFunction.apply((D) e).equals(dtoId))) {
                E entity = em.find(entityClass, dtoId);
                if (entity != null) {
                    entitySet.add(entity);
                }
            }
        }
    }

    public Restaurant fromDTO(Restaurant entity, EntityManager em) {
        if (entity == null) {
            entity = new Restaurant();
        }

        if (this.category != null) {
            entity.setCategory(this.category.fromDTO(entity.getCategory(), em));
        }
        if (this.address != null) {
            entity.setAddress(this.address.fromDTO(entity.getAddress(), em));
        }

        entity.setWebsite(this.website);
        entity.setLocation(this.location);
        entity.setName(this.name);
        entity.setLinkGooglemaps(this.linkGooglemaps);
        entity.setLinkMenu(this.linkMenu);
        entity.setDressCode(this.dressCode);

        syncEntitySet(entity.getPhoneContact(), this.phoneContact, em, PhoneContact.class, NestedPhoneContactDTO::getId);
        syncEntitySet(entity.getRecommendation(), this.recommendation, em, Recommendation.class, NestedRecommendationDTO::getId);
        syncEntitySet(entity.getTimesOfService(), this.timesOfService, em, TimesOfService.class, NestedTimesOfServiceDTO::getId);

        return em.merge(entity);
    }



//	public RestaurantDTO(final Restaurant entity) {
//		if (entity != null) {
//			this.id = entity.getId();
//			this.category = new NestedCategoryDTO(entity.getCategory());
//			this.address = new AddressDTO(entity.getAddress());
//			this.website = entity.getWebsite();
//			this.location = entity.getLocation();
//			this.name = entity.getName();
//			Iterator<PhoneContact> iterPhoneContact = entity.getPhoneContact().iterator();
//			for (; iterPhoneContact.hasNext();) {
//				PhoneContact element = iterPhoneContact.next();
//				this.phoneContact.add(new NestedPhoneContactDTO(element));
//			}
//			Iterator<Recommendation> iterRecommendation = entity.getRecommendation().iterator();
//			for (; iterRecommendation.hasNext();) {
//				Recommendation element = iterRecommendation.next();
//				this.recommendation.add(new NestedRecommendationDTO(element));
//			}
//			this.linkGooglemaps = entity.getLinkGooglemaps();
//			Iterator<TimesOfService> iterTimesOfService = entity.getTimesOfService().iterator();
//			for (; iterTimesOfService.hasNext();) {
//				TimesOfService element = iterTimesOfService.next();
//				this.timesOfService.add(new NestedTimesOfServiceDTO(element));
//			}
//			this.linkMenu = entity.getLinkMenu();
//			this.dressCode = entity.getDressCode();
//		}
//	}

//	public Restaurant fromDTO(Restaurant entity, EntityManager em) {
//		if (entity == null) {
//			entity = new Restaurant();
//		}
//		if (this.category != null) {
//			entity.setCategory(this.category.fromDTO(entity.getCategory(), em));
//		}
//		if (this.address != null) {
//			entity.setAddress(this.address.fromDTO(entity.getAddress(), em));
//		}
//		entity.setWebsite(this.website);
//		entity.setLocation(this.location);
//		entity.setName(this.name);
//		Iterator<PhoneContact> iterPhoneContact = entity.getPhoneContact().iterator();
//		for (; iterPhoneContact.hasNext();) {
//			boolean found = false;
//			PhoneContact phoneContact = iterPhoneContact.next();
//			Iterator<NestedPhoneContactDTO> iterDtoPhoneContact = this.getPhoneContact().iterator();
//			for (; iterDtoPhoneContact.hasNext();) {
//				NestedPhoneContactDTO dtoPhoneContact = iterDtoPhoneContact.next();
//				if (dtoPhoneContact.getId().equals(phoneContact.getId())) {
//					found = true;
//					break;
//				}
//			}
//			if (found == false) {
//				iterPhoneContact.remove();
//			}
//		}
//		Iterator<NestedPhoneContactDTO> iterDtoPhoneContact = this.getPhoneContact().iterator();
//		for (; iterDtoPhoneContact.hasNext();) {
//			boolean found = false;
//			NestedPhoneContactDTO dtoPhoneContact = iterDtoPhoneContact.next();
//			iterPhoneContact = entity.getPhoneContact().iterator();
//			for (; iterPhoneContact.hasNext();) {
//				PhoneContact phoneContact = iterPhoneContact.next();
//				if (dtoPhoneContact.getId().equals(phoneContact.getId())) {
//					found = true;
//					break;
//				}
//			}
//			if (found == false) {
//				Iterator<PhoneContact> resultIter = em
//						.createQuery("SELECT DISTINCT p FROM PhoneContact p", PhoneContact.class).getResultList()
//						.iterator();
//				for (; resultIter.hasNext();) {
//					PhoneContact result = resultIter.next();
//					if (result.getId().equals(dtoPhoneContact.getId())) {
//						entity.getPhoneContact().add(result);
//						break;
//					}
//				}
//			}
//		}
//		Iterator<Recommendation> iterRecommendation = entity.getRecommendation().iterator();
//		for (; iterRecommendation.hasNext();) {
//			boolean found = false;
//			Recommendation recommendation = iterRecommendation.next();
//			Iterator<NestedRecommendationDTO> iterDtoRecommendation = this.getRecommendation().iterator();
//			for (; iterDtoRecommendation.hasNext();) {
//				NestedRecommendationDTO dtoRecommendation = iterDtoRecommendation.next();
//				if (dtoRecommendation.getId().equals(recommendation.getId())) {
//					found = true;
//					break;
//				}
//			}
//			if (found == false) {
//				iterRecommendation.remove();
//			}
//		}
//		Iterator<NestedRecommendationDTO> iterDtoRecommendation = this.getRecommendation().iterator();
//		for (; iterDtoRecommendation.hasNext();) {
//			boolean found = false;
//			NestedRecommendationDTO dtoRecommendation = iterDtoRecommendation.next();
//			iterRecommendation = entity.getRecommendation().iterator();
//			for (; iterRecommendation.hasNext();) {
//				Recommendation recommendation = iterRecommendation.next();
//				if (dtoRecommendation.getId().equals(recommendation.getId())) {
//					found = true;
//					break;
//				}
//			}
//			if (found == false) {
//				Iterator<Recommendation> resultIter = em
//						.createQuery("SELECT DISTINCT r FROM Recommendation r", Recommendation.class).getResultList()
//						.iterator();
//				for (; resultIter.hasNext();) {
//					Recommendation result = resultIter.next();
//					if (result.getId().equals(dtoRecommendation.getId())) {
//						entity.getRecommendation().add(result);
//						break;
//					}
//				}
//			}
//		}
//		entity.setLinkGooglemaps(this.linkGooglemaps);
//		Iterator<TimesOfService> iterTimesOfService = entity.getTimesOfService().iterator();
//		for (; iterTimesOfService.hasNext();) {
//			boolean found = false;
//			TimesOfService timesOfService = iterTimesOfService.next();
//			Iterator<NestedTimesOfServiceDTO> iterDtoTimesOfService = this.getTimesOfService().iterator();
//			for (; iterDtoTimesOfService.hasNext();) {
//				NestedTimesOfServiceDTO dtoTimesOfService = iterDtoTimesOfService.next();
//				if (dtoTimesOfService.getId().equals(timesOfService.getId())) {
//					found = true;
//					break;
//				}
//			}
//			if (found == false) {
//				iterTimesOfService.remove();
//			}
//		}
//		Iterator<NestedTimesOfServiceDTO> iterDtoTimesOfService = this.getTimesOfService().iterator();
//		for (; iterDtoTimesOfService.hasNext();) {
//			boolean found = false;
//			NestedTimesOfServiceDTO dtoTimesOfService = iterDtoTimesOfService.next();
//			iterTimesOfService = entity.getTimesOfService().iterator();
//			for (; iterTimesOfService.hasNext();) {
//				TimesOfService timesOfService = iterTimesOfService.next();
//				if (dtoTimesOfService.getId().equals(timesOfService.getId())) {
//					found = true;
//					break;
//				}
//			}
//			if (found == false) {
//				Iterator<TimesOfService> resultIter = em
//						.createQuery("SELECT DISTINCT t FROM TimesOfService t", TimesOfService.class).getResultList()
//						.iterator();
//				for (; resultIter.hasNext();) {
//					TimesOfService result = resultIter.next();
//					if (result.getId().equals(dtoTimesOfService.getId())) {
//						entity.getTimesOfService().add(result);
//						break;
//					}
//				}
//			}
//		}
//		entity.setLinkMenu(this.linkMenu);
//		entity.setDressCode(this.dressCode);
//		entity = em.merge(entity);
//		return entity;
//	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public NestedCategoryDTO getCategory() {
		return this.category;
	}

	public void setCategory(final NestedCategoryDTO category) {
		this.category = category;
	}

	public AddressDTO getAddress() {
		return this.address;
	}

	public void setAddress(final AddressDTO address) {
		this.address = address;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<NestedPhoneContactDTO> getPhoneContact() {
		return this.phoneContact;
	}

	public void setPhoneContact(final Set<NestedPhoneContactDTO> phoneContact) {
		this.phoneContact = phoneContact;
	}

	public Set<NestedRecommendationDTO> getRecommendation() {
		return this.recommendation;
	}

	public void setRecommendation(final Set<NestedRecommendationDTO> recommendation) {
		this.recommendation = recommendation;
	}

	public String getLinkGooglemaps() {
		return this.linkGooglemaps;
	}

	public void setLinkGooglemaps(final String linkGooglemaps) {
		this.linkGooglemaps = linkGooglemaps;
	}

	public Set<NestedTimesOfServiceDTO> getTimesOfService() {
		return this.timesOfService;
	}

	public void setTimesOfService(final Set<NestedTimesOfServiceDTO> timesOfService) {
		this.timesOfService = timesOfService;
	}

	public String getLinkMenu() {
		return this.linkMenu;
	}

	public void setLinkMenu(final String linkMenu) {
		this.linkMenu = linkMenu;
	}

	public String getDressCode() {
		return this.dressCode;
	}

	public void setDressCode(final String dressCode) {
		this.dressCode = dressCode;
	}
}