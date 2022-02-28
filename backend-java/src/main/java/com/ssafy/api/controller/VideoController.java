package com.ssafy.api.controller;


import com.ssafy.api.advice.exception.CFileNotFoundException;
import com.ssafy.api.dto.ImageDto;
import com.ssafy.api.dto.VideoDto;
import com.ssafy.api.service.VideoService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Image;
import com.ssafy.db.entity.Video;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Api(value = "동영상 API", tags = {"Video"})
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @Autowired
    ServletContext servletContext;
    @PostMapping()
    @ApiOperation(value="비디오 업로드", notes="비디오 업로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addVideo (
            @RequestParam(value="upfile", required = true) MultipartFile[] files ,@RequestParam(value="uploadDate")String uploadDate,@RequestParam(value="teamId")Long teamId, @ApiIgnore Authentication authentication) throws Exception {

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        //System.out.println(servletContext.getRealPath("/resources/dist/video"));
        //String realPath = servletContext.getRealPath("/resources/dist/video");
        String realPath =  System.getProperty("user.home") + "\\files"+"\\video";
        System.out.println(realPath);
        System.out.println(userId);
        StringTokenizer st = new StringTokenizer(uploadDate,"-");
        String ccurday = "";
        while(st.hasMoreElements()){
            ccurday+=st.nextToken();
        }
        String saveFolder = realPath + File.separator + ccurday;

        File folder = new File(saveFolder);
        if(!folder.exists())
            folder.mkdirs();

        for(MultipartFile mfile : files){
            VideoDto videoDto = new VideoDto();
            String originalFileName = mfile.getOriginalFilename();
            if(!originalFileName.isEmpty()){
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                //유일한 식별자를 생성한다.
                videoDto.setFilePath(ccurday);
                videoDto.setOriginalFileName(originalFileName);
                videoDto.setFileName(saveFileName);
                videoDto.setUploadDate(LocalDate.parse(uploadDate));
                System.out.println("원본 파일 이름 : "+ mfile.getOriginalFilename() + " 실제 저장 파일이름 : " + saveFileName);
                mfile.transferTo(new File(folder, saveFileName));
            }
            videoService.addVideo(videoDto,userId,teamId);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/download")
    @ApiOperation(value="비디오 다운로드", notes="비디오 다운로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<UrlResource> download (
            @RequestBody VideoDto videoDto, HttpServletRequest request) throws Exception {
//		System.out.println(param);
        String filePath = System.getProperty("user.home") + "\\files"+"\\video"+ File.separator + videoDto.getFilePath() + File.separator + videoDto.getFileName();
        // String filePath = servletContext.getRealPath("/resources/dist/video") + File.separator + imageDto.getFilePath() + File.separator + imageDto.getOriginalFileName();
        File target = new File(filePath);
        System.out.println(target);
        // request header의 정보 가져오는 법
        HttpHeaders header = new HttpHeaders();
        UrlResource rs = null;
        if(target.exists()) {
            try {
                String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
                System.out.println("mimeType : "+mimeType);
                if(mimeType == null) {
                    mimeType = "application/download; charset=UTF-8";
                }
                rs = new UrlResource(target.toURI());
                String userAgent = request.getHeader("User-Agent");
                boolean isIE = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1;
                String fileName = null;
//				String originalFile = (String) param.get("ofile");
                String originalFile = videoDto.getOriginalFileName();
                // IE는 다르게 처리
                if (isIE) {
                    fileName = URLEncoder.encode(originalFile, "UTF-8").replaceAll("\\+", "%20");
                } else {
                    fileName = new String(originalFile.getBytes("UTF-8"), "ISO-8859-1");
                }
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName +"\"");
                header.setCacheControl("no-cache");
                header.setContentType(MediaType.parseMediaType(mimeType));
                System.out.println("header: "+header);
                System.out.println("rs: "+rs);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(200).headers(header).body(rs);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<? extends BaseResponseBody>  delete(@PathVariable("no") Long no) throws Exception {
        //String path=servletContext.getRealPath("/resources/dist/img");
        String path =  System.getProperty("user.home") + "\\files"+"\\video";
        if(!videoService.deleteVideo(no,
                path)){
            throw new CFileNotFoundException();
        };
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
    @GetMapping("/{no}")
    public StreamingResponseBody stream(@PathVariable("no") Long no) throws Exception {
        Optional<Video> image = videoService.findById(no);
        String Filepath = System.getProperty("user.home")+ "\\files"+"\\video"+ File.separator + image.get().getFilePath() + File.separator + image.get().getFileName();
        File file = new File(Filepath);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }

    @GetMapping(path="/{teamId}/{uploadDate}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 이미지 조회", notes = "team Id, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<String>> getTeamsImageListAtDate(@ApiParam(value = "teamName", required = true) @PathVariable("teamId") Long teamId,
                                                                @ApiParam(value = "date", required = true) @PathVariable("uploadDate") String cDate){
        List<VideoDto> videoDto = videoService.getVideoListAtDateByTeamId(cDate,teamId);

        String imageBasePath = "http://localhost:8080/api/v1/video/";
        List<String> pathList = new ArrayList<>();
        for(int i=0; i<videoDto.size(); i++){
            pathList.add(imageBasePath+videoDto.get(i).getId());
        }

        return ResponseEntity.status(200).body(pathList);
    }

    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}
