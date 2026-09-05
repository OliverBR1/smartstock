package oliver.tech.smartstock.repository;

import oliver.tech.smartstock.entity.PurchaseRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRequestRepository extends MongoRepository<PurchaseRequestEntity, String> {
}
