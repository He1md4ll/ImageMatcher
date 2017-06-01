package edu.hsbremen.cloud.persistance.domain;

import edu.hsbremen.cloud.dto.BlobDto;
import edu.hsbremen.cloud.persistance.converter.BlobToStringConverter;

import javax.persistence.*;

@Entity
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1024)
    @Convert(converter = BlobToStringConverter.class)
    private BlobDto imageBlob;

    @Column(length = 1024)
    @Convert(converter = BlobToStringConverter.class)
    private BlobDto thumbnailBlob;

    @ManyToOne(cascade = CascadeType.MERGE)
    private UserEntity user;

    public ImageEntity() {
    }

    public BlobDto getThumbnailBlob() {
        return thumbnailBlob;
    }

    public void setThumbnailBlob(BlobDto thumbnailBlob) {
        this.thumbnailBlob = thumbnailBlob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlobDto getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(BlobDto imageBlob) {
        this.imageBlob = imageBlob;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}