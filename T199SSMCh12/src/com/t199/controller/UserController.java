package com.t199.controller;

import java.io.File;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.Constants;
import com.t199.entity.User;
import com.t199.service.UserService;
import com.t199.util.Page;

import php.java.bridge.http.HttpResponse;
import com.t199.util.Contants;
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Resource(name="uService")
	private UserService uService;
	
	public UserController(){
		System.out.println("===================UserController===================");
	}

	@RequestMapping("/login")
	public String login(String userCode,String userPassword,Model model,
			HttpSession session/*,HttpServletRequest request,HttpResponse response*/){
		User user=uService.login(userCode, userPassword);
		if(user!=null){//登陆成功
			session.setAttribute(Contants.USER_SESSION, user);
			//return "redirect:/user/main.html";
		}else{//登陆失败
			model.addAttribute("error", "用户名或密码错误");
			//System.out.println(2/0);
			/*return "../../login";*/
			throw new RuntimeException("用户名或密码错误");
		}
		return "redirect:/user/main.html";
	}

	@RequestMapping("/main.html")
	public String main(){
		return "frame";
	}

	//分页查询用户信息
	@RequestMapping("/userlist.html")
	public String getUserByPage(String queryname, Integer pageIndex,Model model){
		HashMap<String, Object> hash=new HashMap<String, Object>();
		hash.put("curpage",pageIndex );
		hash.put("queryname", queryname);
		hash.put("pagesize", 5);
		Page page= uService.getUserByPage(hash);
		model.addAttribute("page", page);
		model.addAttribute("queryname", queryname);
		return "userlist";
	}
	//跳转到新增界面
	@RequestMapping(value="/addUser.html",method=RequestMethod.GET)
	public String toUserAdd(@ModelAttribute("user")User user){
		return "useradd";
	}

	//执行新增功能:单文件上传
	/*	@RequestMapping(value="/addUser.html",method=RequestMethod.POST)
	public String addUser(@Valid User user,BindingResult bindResult,HttpSession session,
			 @RequestParam(value ="attachs", required = false) MultipartFile attach,HttpServletRequest request){
		if(bindResult.hasErrors()){//验证错误，返回true
			return "useradd";
		}
		String idPicPath = null;
		//判断文件是否为空
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("static"+File.separator+"uploadfiles"); 
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
			int filesize = 600000;
	        if(attach.getSize() >  filesize){//上传大小不得超过 5000k
            	request.setAttribute("uploadFileError", " * 上传大小不得超过 500k");
	        	return "useradd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal."+prefix;  
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  

                try {  
                	//保存  
                	attach.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    request.setAttribute("uploadFileError", " * 上传失败！");
                    return "useradd";
                }  
                idPicPath = path+File.separator+fileName;
            }else{
            	request.setAttribute("uploadFileError", " * 上传图片格式不正确");
            	return "useradd";
            }
		}
		User loginuser=(User)session.getAttribute(Contants.USER_SESSION);
		user.setCreatedBy(loginuser.getId());
		user.setIdPicPath(idPicPath);
		Integer result= uService.addUser(user);
		if(result>0){
			return "redirect:userlist.html";
		}else{
			return "useradd";
		}
	}*/

	@RequestMapping(value="/addUser.html",method=RequestMethod.POST)
	public String addUser(/*@Valid*/ /*User user,BindingResult bindResult,*/HttpSession session,
			@RequestParam(value ="attachs", required = false) MultipartFile[] attachs,HttpServletRequest request,Date birthday){
		System.out.println(birthday);
		/*if(bindResult.hasErrors()){//验证错误，返回true
			return "useradd";
		}*/
		String idPicPath = null;
		String workPicPath = null;
		String errorInfo = null;
		boolean flag = true;
		String path = request.getSession().getServletContext().getRealPath("static"+File.separator+"uploadfiles"); 
		if(attachs!=null && attachs.length>0){
		for(int i = 0;i < attachs.length ;i++){
			MultipartFile attach = attachs[i];//提取一个文件
			if(!attach.isEmpty()){
				if(i == 0){
					errorInfo = "uploadFileError";
				}else if(i == 1){
					errorInfo = "uploadWpError";
				}
				String oldFileName = attach.getOriginalFilename();//原文件名
				String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
				int filesize = 500000;
				if(attach.getSize() >  filesize){//上传大小不得超过 500k
					request.setAttribute(errorInfo, " * 上传大小不得超过 500k");
					flag = false;
				}else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
						|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
					String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";  
					File targetFile = new File(path, fileName);  
					if(!targetFile.exists()){  
						targetFile.mkdirs();  
					}  
					//保存  
					try {  
						attach.transferTo(targetFile);  
					} catch (Exception e) {  
						e.printStackTrace();  
						request.setAttribute(errorInfo, " * 上传失败！");
						flag = false;
					}  
					if(i == 0){
						idPicPath = path+File.separator+fileName;
					}else if(i == 1){
						workPicPath = path+File.separator+fileName;
					}

				}else{
					request.setAttribute(errorInfo, " * 上传图片格式不正确");
					flag = false;
				}
			}
		}
		}
		/*User loginuser=(User)session.getAttribute(Contants.USER_SESSION);
		user.setCreatedBy(loginuser.getId());
		user.setIdPicPath(idPicPath);
		user.setWorkPicPath(workPicPath);
		Integer result= uService.addUser(user);
		if(result>0){
			return "redirect:userlist.html";
		}else{
			return "useradd";
		}*/
		return "useradd";
	}

/*	@RequestMapping("/getUserById/{id}")
	public String getUserById(@PathVariable Integer id,Model model){
		System.out.println("id:"+id);
		User user=uService.getUserById(id);
		model.addAttribute("user", user);
		return "userview";
	}
*/
	@RequestMapping(value="/getUserById/{id}"/*,produces={"application/json;charset=UTF-8"}*/)
	@ResponseBody
	public Object getUserById(@PathVariable Integer id,Model model){
		System.out.println("id:"+id);
		User user=uService.getUserById(id);
		if(user==null){
			return "nodata";
		}
		return user;
		//return JSON.toJSONString(user);
	}
	@RequestMapping(value="/modifyUser.html",method=RequestMethod.GET)
	public String modifyUser(Integer id,Model model){
		User user=uService.getUserById(id);
		model.addAttribute("user", user);
		return "usermodify";
	}

	@RequestMapping(value="/modifyUser.html",method=RequestMethod.POST)
	public String modifyUser(User user,Model model){
		Integer result=uService.updateUser(user);
		if(result>0){
			return "redirect:userlist.html";
		}else{
			return "usermodify";
		}
	}


	//局部异常处理，只对当前controler有效
	/*@ExceptionHandler(value={RuntimeException.class})
	public String hadlerException(RuntimeException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "../../error";
	}*/

	//文件下载
	@RequestMapping("download")    
	public ResponseEntity<byte[]> download(String dfileName,HttpServletRequest request) throws IOException {    
		String path = request.getSession().getServletContext().getRealPath("static"+File.separator+"uploadfiles"); 
		File file=new File(path+File.separator+dfileName);  
		HttpHeaders headers = new HttpHeaders();    
		//String fileName=new String(dfileName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
		headers.setContentDispositionFormData("attachment", dfileName);   
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
				headers, HttpStatus.CREATED);    
	}  
	
	//验证用户code是否存在
	@RequestMapping("/checkCode")
	@ResponseBody
	public Object checkCode(String userCode){
		User user=uService.getUserByCode(userCode);
		HashMap<String, Object> hash=new HashMap<String,Object>();
		if(user!=null){
			hash.put("userCode", "exist");
		}else{
			hash.put("userCode", "notexist");
		}
		//return hash;
		return JSONArray.toJSONString(hash);
	}


	public UserService getuService() {
		return uService;
	}

	public void setuService(UserService uService) {
		this.uService = uService;
	}

}
