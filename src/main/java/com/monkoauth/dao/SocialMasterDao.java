package com.monkoauth.dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.monkoauth.model.SocialMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.monkoauth.repo.SocialMasterRepo;

import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;

@Repository
public class SocialMasterDao implements SocialMasterRepo {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public <S extends SocialMaster> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocialMaster> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocialMaster> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SocialMaster> S insert(S entity) {
		mongoTemplate.insert(entity);
		return entity;
	}

	@Override
	public <S extends SocialMaster> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SocialMaster> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SocialMaster> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SocialMaster> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SocialMaster> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SocialMaster> findById(String id) {
		return Optional.ofNullable(mongoTemplate.findById(id, SocialMaster.class));
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<SocialMaster> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		
		Optional<SocialMaster> searchResult = findById(id);
		
		if(searchResult.isPresent()) {
			mongoTemplate.remove(searchResult.get());
		}
	}

	@Override
	public void delete(SocialMaster entity) {
		mongoTemplate.remove(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SocialMaster> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends SocialMaster> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SocialMaster> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SocialMaster> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends SocialMaster> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public SocialMaster findByImcId(String imcId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("imcId").is(imcId));
		
		return mongoTemplate.findOne(query, SocialMaster.class);
		
	}

	public SocialMaster findValidAuthToken(String socialHandleId , String clientId) {
		AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "createdOn");

		AggregationOperation matchSocialHandleId =
				Aggregation.match(new Criteria().andOperator(
						Criteria.where("socialHandleId").is(socialHandleId) ,
						Criteria.where("clientId").is(clientId),
						Criteria.where("expiryDate").gte(Instant.now())
				));

		Aggregation aggregation = Aggregation.newAggregation(matchSocialHandleId, sort, Aggregation.limit(1));
		AggregationResults<SocialMaster> aggregationResults = mongoTemplate.aggregate(aggregation , "SocialMaster" , SocialMaster.class);
		return aggregationResults.getUniqueMappedResult();
	}


}
