package com.springboot.entrename.api.security.authorization;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {
    public @interface Public {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("permitAll()")
        public @interface canRead {}
    }

    public @interface Protected {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.isAuthenticated")
        public @interface canManage {}
    }

    public @interface Logout {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.ableBlacklisted")
        public @interface canBlacklisted {}
    }

    public @interface Profile {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.isProfileOwner(#username)")
        public @interface canManage {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.isProfileNonOwner(#username)")
        public @interface canFollow {}
    }

    public @interface Comments {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.isCommentAuthor(#slugComment)")
        public @interface canDelete {}
    }

    public @interface Inscriptions {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.ableModifyInscription(#slugInscription)")
        public @interface canDelete {}
    }
}
