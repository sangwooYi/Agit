package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CFileNotFoundException;
import com.ssafy.api.advice.exception.CTokenForbiddenException;
import com.ssafy.api.dto.ArticleDto;
import com.ssafy.api.dto.ImageDto;
import com.ssafy.api.service.ImageService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Image;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Api(value = "이미지 API", tags = {"Image"})
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    ServletContext servletContext;

    @PostMapping()
    @ApiOperation(value="이미지 업로드", notes="이미지 업로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addImage (
           @RequestParam(value="upfile", required = true) MultipartFile[] files ,@RequestParam(value="uploadDate")String uploadDate,@RequestParam(value="teamId")Long teamId, @ApiIgnore Authentication authentication) throws Exception {

        //String userId="bomin2641@gmail.com";

            SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();

        //System.out.println(servletContext.getRealPath("/resources/dist/img"));
        //String realPath = servletContext.getRealPath("/resources/dist/img");
        String realPath =  System.getProperty("user.home") + "\\files"+"\\image";
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
            ImageDto imageDto = new ImageDto();
            String originalFileName = mfile.getOriginalFilename();
            if(!originalFileName.isEmpty()){
                System.out.println("checkcheck" + originalFileName);
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                //유일한 식별자를 생성한다.
                imageDto.setFilePath(ccurday);
                imageDto.setOriginalFileName(originalFileName);
                imageDto.setFileName(saveFileName);
                imageDto.setUploadDate(LocalDate.parse(uploadDate));
                System.out.println("원본 파일 이름 : "+ mfile.getOriginalFilename() + " 실제 저장 파일이름 : " + saveFileName);
                mfile.transferTo(new File(folder, saveFileName));
            }
            imageService.addImage(imageDto,userId,teamId);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/back")
    @ApiOperation(value="배경이미지 업로드", notes="이미지 업로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addBackImage (
            @RequestParam(value="image", required = true) MultipartFile[] files) throws Exception {


        String realPath =  System.getProperty("user.home") + "\\files"+"\\backimage";
        System.out.println(realPath);


        String saveFolder = realPath;

        File folder = new File(saveFolder);
        if(!folder.exists())
            folder.mkdirs();

        for(MultipartFile mfile : files){
            ImageDto imageDto = new ImageDto();
            String originalFileName = mfile.getOriginalFilename();
            if(!originalFileName.isEmpty()){
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                //유일한 식별자를 생성한다.

                System.out.println("원본 파일 이름 : "+ mfile.getOriginalFilename() + " 실제 저장 파일이름 : " + saveFileName);
                mfile.transferTo(new File(folder, saveFileName));
            }
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/download")
    @ApiOperation(value="이미지 다운로드", notes="이미지 다운로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<UrlResource> download (
            @RequestBody ImageDto imageDto, HttpServletRequest request) throws Exception {
//		System.out.println(param);
		String filePath = System.getProperty("user.home") + "\\files"+"\\image"+ File.separator + imageDto.getFilePath() + File.separator + imageDto.getFileName();
       // String filePath = servletContext.getRealPath("/resources/dist/img") + File.separator + imageDto.getFilePath() + File.separator + imageDto.getOriginalFileName();
        File target = new File(filePath);
        System.out.println(target);
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
                String originalFile = imageDto.getOriginalFileName();
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
        String path =  System.getProperty("user.home") + "\\files"+"\\image";
        if(!imageService.deleteNotice(no,
                path)){
            throw new CFileNotFoundException();
        };
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping(path="/{teamId}/{uploadDate}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 이미지 조회", notes = "team Id, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<String>> getTeamsImageListAtDate(@ApiParam(value = "teamName", required = true) @PathVariable("teamId") Long teamId,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("uploadDate") String cDate){
        List<ImageDto> imageDto = imageService.getImageListAtDateByTeamId(cDate, teamId);
        String imageBasePath = "http://localhost:8080/api/v1/image/";
        List<String> pathList = new ArrayList<>();
        for(int i=0; i<imageDto.size(); i++){
            pathList.add(imageBasePath+imageDto.get(i).getId());
        }

        return ResponseEntity.status(200).body(pathList);
    }

    @GetMapping(path="/{no}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("no") Long no) throws IOException {
        Optional<Image> image = imageService.findById(no);
        if(image.isEmpty()){
            System.out.println("빈객체");
        }
        String filePath = System.getProperty("user.home") + "\\files"+"\\image"+ File.separator + image.get().getFilePath() + File.separator + image.get().getFileName();
        File target = new File(filePath);

        byte[] returnValue = null;
        ByteArrayOutputStream baos =null;
        FileInputStream fis = null;

        try{
            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(filePath);

            byte[] buf = new byte[1024];
            int read = 0;

            while((read=fis.read(buf,0,buf.length))!=-1){
                baos.write(buf,0,read);
            }

            returnValue = baos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(baos!=null){
                baos.close();
            }

            if(fis!=null){
                fis.close();
            }
        }
        return ResponseEntity.status(200).body(returnValue);
    }



}
