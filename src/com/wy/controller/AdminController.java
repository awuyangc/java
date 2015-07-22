package com.wy.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wy.model.BaseJsonData;
import com.wy.model.News;
import com.wy.model.NewsFile;
import com.wy.model.NewsType;
import com.wy.model.Picture;
import com.wy.model.User;
import com.wy.service.INewsFileService;
import com.wy.service.INewsService;
import com.wy.service.INewsTypeService;
import com.wy.service.IPictureService;
import com.wy.service.ISysParamService;
import com.wy.service.IUserService;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Resource
	private IUserService userService;
	@Resource
	private INewsTypeService newsTypeService;
	@Resource
	private ISysParamService sysParamService;
	@Resource
	private INewsService newsService;
	@Resource
	private INewsFileService newsFileService;
	@Resource
	private IPictureService pictureService;
	
	public Map<String, String> map = new HashMap<String, String>();
	
	@RequestMapping("/admin_login")
	public String toAdminLogin(HttpServletRequest request,Model model){
		return "admin/login.html";
	}
	
	@RequestMapping("/admin_index")
	public String toAdminIndex(HttpServletRequest request,Model model){
		return "admin/index.html";
	}
	
	@RequestMapping("/admin_dashboard")
	public String toAdminDashboard(HttpServletRequest request,Model model){
		return "admin/dashboard.html";
	}
	
//*********************************************用户管理***********************************************	
	@RequestMapping("/admin_sysuser")
	public String toAdminSysuser(HttpServletRequest request,Model model){
		return "admin/sysuser.html";
	}
	
	//获取系统用户列表，一次性加载
	@RequestMapping("/getSysUser")
	@ResponseBody
	public BaseJsonData getSysUser(HttpServletRequest request,Model model){
		List<User> userList=userService.getUserList();
		BaseJsonData baseJsonDate=new BaseJsonData();
		baseJsonDate.setData(userList);
		return baseJsonDate;
	}
	
	@RequestMapping("/cpu_chart")
	@ResponseBody
	public String cpu_chart(HttpServletRequest request,Model model){
		return "50";
	}
	
	//新增用户
	@RequestMapping("/addUser")
	@ResponseBody
	public void addSysUser(@ModelAttribute("user") User user){
		user.setCreateTime(new Date());
		userService.insert(user);
	}
	
	//修改用户
	@RequestMapping("/editUser")
	@ResponseBody
	public void editSysUser(@ModelAttribute("user") User user){
		userService.update(user);
	}
	
	//删除用户
	@RequestMapping("/deleteUser")
	@ResponseBody
	public void deleteSysUser(@ModelAttribute("user") User user){
		userService.delete(user);
	}
	
//*********************************************新闻分类管理***********************************************	
	@RequestMapping("/newsType")
	public String toNewsType(HttpServletRequest request,Model model){
		return "admin/newsType.html";
	}
	
	//获取新闻分类列表，一次性加载
	@RequestMapping("/getNewsType")
	@ResponseBody
	public BaseJsonData getNewsType(HttpServletRequest request,Model model){
		List<NewsType> newsTypeList=newsTypeService.getNewsTypeList();
		BaseJsonData baseJsonDate=new BaseJsonData();
		baseJsonDate.setData(newsTypeList);
		return baseJsonDate;
	}
	
	@RequestMapping("/getNewsTypeEnable")
	@ResponseBody
	public BaseJsonData getNewsTypeEnable(HttpServletRequest request,Model model){
		List<NewsType> newsTypeList=newsTypeService.selectAllEnable();
		BaseJsonData baseJsonDate=new BaseJsonData();
		baseJsonDate.setData(newsTypeList);
		return baseJsonDate;
	}
	
	
	//新增分类
	@RequestMapping("/addNewsType")
	@ResponseBody
	public void addNewsType(@ModelAttribute("newsType") NewsType newsType){
		newsTypeService.insert(newsType);
	}
	
	//修改分类
	@RequestMapping("/editNewsType")
	@ResponseBody
	public void editNewsType(@ModelAttribute("newsType") NewsType newsType){
		newsTypeService.update(newsType);
	}
	
	//删除分类
	@RequestMapping("/deleteNewsType")
	@ResponseBody
	public void deleteNewsType(@ModelAttribute("newsType") NewsType newsType){
		newsTypeService.delete(newsType);
	}

//*********************************************添加新闻***********************************************	
	@RequestMapping("/addNews")
	public String toAddNews(HttpServletRequest request,Model model){
		return "admin/addNews.html";
	}
	
	
	//附件上传
	@RequestMapping("/fileUpload")
	@ResponseBody
	public String fileUpload(HttpServletRequest request,Model model,@RequestParam(value="file", required=false) MultipartFile file) throws IOException{
		byte[] bytes = file.getBytes(); 
        //获取年月日，时分组成的路径
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String sep = System.getProperty("file.separator"); 
        String timePath=cal.get(Calendar.YEAR)+sep+(cal.get(Calendar.MONTH)+1)+sep+cal.get(Calendar.DAY_OF_MONTH)+sep+cal.get(Calendar.HOUR_OF_DAY)+sep+cal.get(Calendar.MINUTE);
        String uploadDir =sysParamService.getSysParamByName("filepath").getSysParamValue()+sep+timePath;  
        File dirPath = new File(uploadDir);  
        if (!dirPath.exists()) {  
            dirPath.mkdirs();  
        } 
        File uploadedFile = new File(uploadDir + sep  
                + file.getOriginalFilename());  
        FileCopyUtils.copy(bytes, uploadedFile);
        //返回文件名和路径名
        return timePath;
	}
	//发布和保存新闻
	@RequestMapping("/doNews")
	@ResponseBody
	public int doNews(News news,@RequestParam(value="newsFileJson") String newsFileJson) throws JsonParseException, JsonMappingException, IOException{
		//在service层去处理同时添加到两个表的情况。此处使用事务注解貌似无效
		ObjectMapper mapper  = new ObjectMapper(); 
		JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, NewsFile.class);	 
		List<NewsFile> newsFileList= mapper.readValue(newsFileJson,javaType);
		news.setCreateTime(new Date());
		newsService.insert(news,newsFileList);	
		return news.getNewsId();
	}
	
//*********************************************管理新闻***********************************************
	@RequestMapping("/editNews")
	public String toEditNews(HttpServletRequest request,Model model){
		return "admin/editNews.html";
	}
	
	//获取新闻列表
	@RequestMapping("/getNewsList")
	@ResponseBody
	public BaseJsonData getNewsList(HttpServletRequest request,Model model){
		List<News> newsList=newsService.selectAll(map);
		BaseJsonData baseJsonDate=new BaseJsonData();
		baseJsonDate.setData(newsList);
		return baseJsonDate;
	}
	
	//获取新闻
		@RequestMapping("/getNews")
		@ResponseBody
		public News getNews(Integer newsId){
			return newsService.selectByPrimaryKey(newsId);
		}
	
	//发布、取消发布新闻
	@RequestMapping("/publishNews")
	@ResponseBody
	public void publishNews(News news){
		newsService.updatePublish(news);
	}
	
	//置顶、取消置顶新闻
	@RequestMapping("/topNews")
	@ResponseBody
	public void topNews(News news){
		newsService.updateTop(news);
	}
	
	//删除新闻，假删除
	@RequestMapping("/deleteNews")
	@ResponseBody
	public void deleteNews(News news){
		newsService.updateDelete(news);
	}
	
	//附件查询
	@RequestMapping("/getNewsFile")
	@ResponseBody
	public BaseJsonData getNewsFile(NewsFile newsFile){
		List<NewsFile> newsFileList=newsFileService.selectByNewsId(newsFile.getNewsId());
		BaseJsonData baseJsonDate=new BaseJsonData();
		baseJsonDate.setData(newsFileList); 
		return baseJsonDate;
	}
	
	//修改新闻
	@RequestMapping("/doEditNews")
	@ResponseBody
	public int doEditNews(News news,@RequestParam(value="newsFileJson") String newsFileJson) throws JsonParseException, JsonMappingException, IOException{
		//在service层去处理同时添加到两个表的情况。此处使用事务注解貌似无效
		ObjectMapper mapper  = new ObjectMapper(); 
		JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, NewsFile.class);	 
		List<NewsFile> newsFileList= mapper.readValue(newsFileJson,javaType);
		news.setCreateTime(new Date());
		newsService.update(news,newsFileList);	
		return news.getNewsId();
	}
//*********************************************图片轮播管理**********************************************
	@RequestMapping("/admin_carousel")
	public String toAdminCarousel(HttpServletRequest request,Model model){
		return "admin/carousel.html";
	}
	
	//获取新闻列表
	@RequestMapping("/getPictureList")
	@ResponseBody
	public BaseJsonData getPictureList(HttpServletRequest request,Model model){
		List<Picture> picture=pictureService.getPictureList();
		BaseJsonData baseJsonDate=new BaseJsonData();
		baseJsonDate.setData(picture);
		return baseJsonDate;
	}
	
	//获取新闻
		@RequestMapping("/getPicture")
		@ResponseBody
		public Picture getPicture(Integer picId){
			Picture picture=pictureService.getPictureById(picId);
			return picture;
		}
	
	//发布和保存新闻
	@RequestMapping("/insertPicture")
	@ResponseBody
	public void insertPicture(Picture picture,@RequestParam(value="picJson") String picJson) throws JsonParseException, JsonMappingException, IOException{
		//在service层去处理同时添加到两个表的情况。此处使用事务注解貌似无效
		ObjectMapper mapper  = new ObjectMapper(); 
		JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, Picture.class);	 
		List<Picture> picList= mapper.readValue(picJson,javaType);
		if(picList.size()!=0)
		{
		picture.setPicName(picList.get(0).getPicName());
		picture.setPicPath(picList.get(0).getPicPath());
		}
		pictureService.insert(picture);
	}
		
	@RequestMapping("/deletePicture")
	@ResponseBody
	public void deletePicture(Integer picId) throws JsonParseException, JsonMappingException, IOException{
		pictureService.delete(picId);
	}
		
	//修改新闻
	@RequestMapping("/editPicture")
	@ResponseBody
	public void editPicture(Picture picture,@RequestParam(value="picJson") String picJson) throws JsonParseException, JsonMappingException, IOException{
		//在service层去处理同时添加到两个表的情况。此处使用事务注解貌似无效
		ObjectMapper mapper  = new ObjectMapper(); 
		JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, Picture.class);	 
		List<Picture> picList= mapper.readValue(picJson,javaType);
		if(picList.size()!=0)
		{
			picture.setPicName(picList.get(0).getPicName());
			picture.setPicPath(picList.get(0).getPicPath());
		}
		pictureService.update(picture);;
	}
}
