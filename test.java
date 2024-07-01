
import test.TraceableGraphQlServiceConfig;
import test.DeepStoreFileSystem;
import test.DeepStoreFileSystemFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileWriter {
  private static final String DOT_DELIMITER = ".";
  private final DeepStoreFileSystem fileSystem;

  @Inject
  public FileWriter(final TraceableGraphQlServiceConfig serviceConfig) {
    this.fileSystem = DeepStoreFileSystemFactory.get(serviceConfig.getFileStoreConfig());
  }

  public void createFileDirectoryIfDoesNotExist(Path dirPath) throws IOException {
    fileSystem.mkdirs(dirPath.toString());
  }

  public void writePayloadToDirectory(byte[] payload, Path dirPath, String fileExtension)
      throws IOException {
    String fileName = String.join(DOT_DELIMITER, UUID.randomUUID().toString(), fileExtension);
    String filePath = Path.of(dirPath.toString(), fileName).toString();
    fileSystem.write(payload, filePath);
  }
