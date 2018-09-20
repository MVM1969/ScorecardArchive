package com.mvm.scorecard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvm.scorecard.model.Scorecard;

@Repository
public interface ScorecardRepository extends JpaRepository<Scorecard, Long> {

}