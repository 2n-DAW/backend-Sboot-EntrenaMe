package com.springboot.entrename.infra.spec;

import com.springboot.entrename.domain.court.CourtEntity;

// import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "sports", alias = "s")
@And({
    @Spec(path = "s.nameSport", params = "sport", spec = Like.class),
})
public interface CourtSpecification extends Specification<CourtEntity> {
}
