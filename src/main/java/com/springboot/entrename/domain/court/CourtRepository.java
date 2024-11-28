package com.springboot.entrename.domain.court;

// import com.springboot.entrename.infra.spec.CourtSpecification;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// import java.util.List;
import java.util.Optional;

@Repository
public interface CourtRepository extends JpaRepository<CourtEntity, Long>, JpaSpecificationExecutor<CourtEntity> {
    // @EntityGraph(attributePaths = {"sports"})
    // Page<CourtEntity> findAllByOrderByIdCourtAsc(Specification<CourtEntity> filter, Pageable pageable);

    Optional<CourtEntity> findBySlugCourt(String slugCourt);
}
