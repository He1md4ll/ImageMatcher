package edu.hsbremen.cloud.persistance;

import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import edu.hsbremen.cloud.dto.BlobDto;
import edu.hsbremen.cloud.dto.ImageHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@Component
public class GoogleBlobStorage implements IBlobStorage {

    private Storage googleBlobStorage;
    private List<Acl> aclReadAccess;
    @Value("${custom.google.datastore.bucket}")
    private String bucketName;

    @PostConstruct
    private void init() {
        googleBlobStorage = StorageOptions.getDefaultInstance().getService();
        aclReadAccess = Lists.newArrayList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
    }

    @Override
    public BlobDto saveImage(final ImageHolder imageHolder) {
        final Blob blob = googleBlobStorage
                .create(BlobInfo.newBuilder(bucketName, imageHolder.getImageName() + "-" + UUID.randomUUID().toString())
                                .setAcl(aclReadAccess)
                                .build(),
                        new ByteArrayInputStream(imageHolder.getImageBytes()));
        return BlobDto.fromBlob(blob);
    }
}