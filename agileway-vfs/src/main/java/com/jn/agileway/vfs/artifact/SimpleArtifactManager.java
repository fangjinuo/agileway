package com.jn.agileway.vfs.artifact;

import com.jn.agileway.vfs.artifact.repository.ArtifactRepository;
import com.jn.langx.annotation.NonNull;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.provider.AbstractFileObject;

import java.util.List;

public class SimpleArtifactManager extends AbstractArtifactManager {
    @NonNull
    private ArtifactRepository repository;

    public ArtifactRepository getRepository() {
        return repository;
    }

    public void setRepository(ArtifactRepository repository) {
        this.repository = repository;
    }

    @Override
    public AbstractFileObject getArtifactFile(Artifact artifact) throws FileSystemException {
        String localPath = repository.getPath(artifact);
        return (AbstractFileObject) getFileSystemManager().resolveFile(localPath);
    }

    @Override
    public List<ArtifactDigit> getDigits(Artifact artifact) {
        return getDigits(this.repository, artifact);
    }
}
