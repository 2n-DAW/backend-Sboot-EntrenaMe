package com.springboot.entrename.domain.profile;

import com.springboot.entrename.domain.user.UserEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Define IdClass para la clave compuesta de FollowEntity
public class FollowId implements Serializable {
    private UserEntity idFollower;
    private UserEntity idFollowed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(idFollower, followId.idFollower) && Objects.equals(idFollowed, followId.idFollowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFollower, idFollowed);
    }
}
