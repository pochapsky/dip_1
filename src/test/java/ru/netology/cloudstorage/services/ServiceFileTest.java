package ru.netology.cloudstorage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.exceptions.DeleteFileExceptionError;
import ru.netology.cloudstorage.exceptions.GettingFileListExceptionError;
import ru.netology.cloudstorage.exceptions.InputDataExceptionError;
import ru.netology.cloudstorage.exceptions.UnauthorizedExceptionError;
import ru.netology.cloudstorage.models.File;
import ru.netology.cloudstorage.models.User;
import ru.netology.cloudstorage.repositories.AuthRepository;
import ru.netology.cloudstorage.repositories.FileRepository;
import ru.netology.cloudstorage.repositories.UserRepository;
import ru.netology.cloudstorage.request.RequestEditFileName;
import ru.netology.cloudstorage.response.ResponseFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceFileTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserRepository userRepository;

    //----------------------------------------------------------------------------------------------------------
    public static final String TOKEN_1 = "Auth_Token1";
    public static final String FILENAME_1 = "Filename1";
    public static final Long AUTH_USER_ID_1 = 101L;
    public static final String AUTH_USERNAME_1 = "Auth_Username1";
    public static final String AUTH_PASSWORD_1 = "Auth_Password1";
    public static final User USER_1 = new User(AUTH_USER_ID_1, AUTH_USERNAME_1, AUTH_PASSWORD_1, null);
    public static final UsernamePasswordAuthenticationToken USERNAME_PASS_AUTH_TOKEN
            = new UsernamePasswordAuthenticationToken(AUTH_USERNAME_1, AUTH_PASSWORD_1);

    public static final Long FILE_ID_1 = 1L;
    public static final Long SIZE_1 = 100L;
    public static final byte[] FILE_CONTENT_1 = FILENAME_1.getBytes();
    public static final File FILE_1 = new File(FILE_ID_1, FILENAME_1, LocalDateTime.now(),
            SIZE_1, FILE_CONTENT_1, USER_1);

    //----------------------------------------------------------------------------------------------------------
    public static final String FILENAME_2 = "Filename2";
    public static final Long AUTH_USER_ID_2 = 101L;
    public static final String AUTH_USERNAME_2 = "Auth_Username2";
    public static final String AUTH_PASSWORD_2 = "Auth_Password2";
    public static final User USER_2 = new User(AUTH_USER_ID_2, AUTH_USERNAME_2, AUTH_PASSWORD_2, null);

    public static final Long FILE_ID_2 = 2L;
    public static final Long SIZE_2 = 200L;
    public static final byte[] FILE_CONTENT_2 = FILENAME_2.getBytes();
    public static final MultipartFile MULTIPART_FILE = new MockMultipartFile(FILENAME_2, FILE_CONTENT_2);
    public static final File FILE_2 =
            new File(FILE_ID_2, FILENAME_2, LocalDateTime.now(), SIZE_2, FILE_CONTENT_2, USER_2);

    // -----------------------------------------------------------------------------------------------------------
    public static final String BEARER_TOKEN = "Bearer Token";
    public static final String BEARER_TOKEN_SPLIT = BEARER_TOKEN.split(" ")[1];
    // -----------------------------------------------------------------------------------------------------------

    public static final String NEW_FILENAME = "Filename_New";
    public static final String FILENAME_EMPTY = null;
    public static final File FILE_NULL = null;
    public static final List<File> FILE_LIST_NULL = null;
    public static final Integer LIMIT_NULL = 0;
    public static final Integer LIMIT = 100;
    public static final RequestEditFileName REQUEST_EDIT_FILE_NAME = new RequestEditFileName(NEW_FILENAME);
    public static final ResponseFile RESPONSE_FILE_1 = new ResponseFile(FILENAME_1, SIZE_1);
    public static final ResponseFile RESPONSE_FILE_2 = new ResponseFile(FILENAME_2, SIZE_2);
    public static final List<ResponseFile> RESPONSE_FILE_LIST = List.of(RESPONSE_FILE_1, RESPONSE_FILE_2);
    public static final List<File> FILE_LIST = List.of(FILE_1, FILE_2);
    // -----------------------------------------------------------------------------------------------------------


    @BeforeEach
    void setUp() {
        Mockito.when(authRepository.getAuthenticationUserByToken(BEARER_TOKEN_SPLIT)).thenReturn(USER_1);
        Mockito.when(userRepository.findUserByLogin(AUTH_USERNAME_1)).thenReturn(USER_1);
    }

    @Test
    void uploadFile() {
        Assertions.assertTrue(fileService.uploadFile(BEARER_TOKEN, FILENAME_1, MULTIPART_FILE));
    }

    @Test
    void uploadFileUnauthorizedException() {
        assertThrows(UnauthorizedExceptionError.class,
                () -> fileService.uploadFile(TOKEN_1, FILENAME_1, MULTIPART_FILE));
    }

    @Test
    void deleteFile() {
        Mockito.when(fileRepository.deleteByUserAndFilename(USER_1, FILENAME_1)).thenReturn(1);
        fileService.deleteFile(BEARER_TOKEN, FILENAME_1);
        Mockito.verify(fileRepository,
                Mockito.times(1)).deleteByUserAndFilename(USER_1, FILENAME_1);
    }

    @Test
    void deleteFileUnauthorizedException() {
        assertThrows(UnauthorizedExceptionError.class, () -> fileService.deleteFile(TOKEN_1, FILENAME_1));
    }

    @Test
    void deleteFileInputDataException() {
        assertThrows(InputDataExceptionError.class, () -> fileService.deleteFile(BEARER_TOKEN, FILENAME_EMPTY));
    }

    @Test
    void errorDeleteFileException() {
        Mockito.when(fileRepository.deleteByUserAndFilename(USER_1, FILENAME_1)).thenReturn(0);
        assertThrows(DeleteFileExceptionError.class, () -> fileService.deleteFile(BEARER_TOKEN, FILENAME_1));
    }

    @Test
    void downloadFileUnauthorizedException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(FILE_1);
        assertThrows(UnauthorizedExceptionError.class, () -> fileService.downloadFile(TOKEN_1, FILENAME_1));
    }

    @Test
    void downloadFileInputDataException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(FILE_1);
        assertThrows(InputDataExceptionError.class, () -> fileService.downloadFile(BEARER_TOKEN, FILENAME_2));
    }

    @Test
    void editFileName() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(FILE_1);
        fileService.editFileName(BEARER_TOKEN, FILENAME_1, REQUEST_EDIT_FILE_NAME);
        Mockito.verify(fileRepository, Mockito.times(1))
                .setNewFilenameByUserAndFilename(NEW_FILENAME, USER_1, FILENAME_1);
    }

    @Test
    void editFileNameUnauthorizedException() {
        assertThrows(UnauthorizedExceptionError.class,
                () -> fileService.editFileName(TOKEN_1, FILENAME_1, REQUEST_EDIT_FILE_NAME));
    }

    @Test
    void editFileNameInputDataException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(FILE_NULL);
        assertThrows(InputDataExceptionError.class,
                () -> fileService.editFileName(BEARER_TOKEN, FILENAME_1, REQUEST_EDIT_FILE_NAME));
    }

    @Test
    void getAllFiles() {
        Mockito.when(fileRepository.findAllByUser(USER_1)).thenReturn(FILE_LIST);
        Assertions.assertEquals(RESPONSE_FILE_LIST, fileService.getAllFiles(BEARER_TOKEN, LIMIT));
    }

    @Test
    void getAllFilesUnauthorizedException() {
        Mockito.when(fileRepository.findAllByUser(USER_1)).thenReturn(FILE_LIST);
        assertThrows(UnauthorizedExceptionError.class, () -> fileService.getAllFiles(TOKEN_1, LIMIT));
    }

    @Test
    void getAllFilesInputDataException() {
        assertThrows(InputDataExceptionError.class, () -> fileService.getAllFiles(BEARER_TOKEN, LIMIT_NULL));
    }

    @Test
    void getAllFilesErrorGettingFileListException() {
        Mockito.when(fileRepository.findAllByUser(USER_1)).thenReturn(FILE_LIST_NULL);
        assertThrows(GettingFileListExceptionError.class, () -> fileService.getAllFiles(BEARER_TOKEN, LIMIT));
    }
}
