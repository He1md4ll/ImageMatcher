package edu.hsbremen.cloud.persistance;

import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import edu.hsbremen.cloud.dto.ImageHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GoogleBlobStorage implements IBlobStorage {

    private Storage googleBlobStorage;
    private List<Acl> aclReadAccess;
    @Value("${custom.google.datastore.bucket}")
    private String bucketName;

    @PostConstruct
    private void init() {;
        googleBlobStorage = StorageOptions.getDefaultInstance().getService();
        aclReadAccess = Lists.newArrayList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
    }

    @Override
    public String saveImage(final ImageHolder imageHolder) {
        final Blob blob = googleBlobStorage
                .create(BlobInfo.newBuilder(bucketName, imageHolder.getImageName())
                                .setAcl(aclReadAccess)
                                .build(),
                        imageHolder.getImageBytes());
        return blob.getMediaLink();
    }
}