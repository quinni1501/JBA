package vn.iotstar.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IVideoService;
import vn.iotstar.services.impl.CategoryService;
import vn.iotstar.services.impl.VideoService;
import vn.iotstar.utils.Constants;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
maxFileSize = 1024 * 1024 * 5, // 5MB
maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/add", "/admin/video/insert",
"/admin/video/delete", "/admin/video/edit", "/admin/video/update" })
public class VideoController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryService();
	public IVideoService vidService = new VideoService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();
		if(url.contains("/admin/video/add")) {
			List<Category> listCategories = cateService.findAll();
            req.setAttribute("listCategories", listCategories);
            req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
		}else if (url.contains("/admin/videos")) {
			List<Video> list = vidService.findAll();
			req.setAttribute("listvid", list);
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
		}else if(url.contains("/admin/video/delete")) {
			String id = req.getParameter("id");
			try {
				vidService.delete(id);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}else if (url.contains("/admin/video/edit")) {
			String id = req.getParameter("id");
			Video video = vidService.findById(id);
			List<Category> listCategories = cateService.findAll();  // Load danh sách Category
			req.setAttribute("vid", video);
			req.setAttribute("listCategories", listCategories);  // Đảm bảo truyền listCategories
			req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    String url = req.getRequestURI();
	    
	    if(url.contains("/admin/video/insert")) {
	    	String title = req.getParameter("title");
	    	String description = req.getParameter("description");
	    	int categoryId = Integer.parseInt(req.getParameter("categoryId"));
	    	int active = Integer.parseInt(req.getParameter("active"));
	    	String poster = req.getParameter("poster");
	    	int views = Integer.parseInt(req.getParameter("views"));
	    	
	    	String videoId = UUID.randomUUID().toString();
	    	Video video = new Video();
	    	video.setVideoId(videoId);
	        video.setTitle(title);
	        video.setDescription(description);
	        video.setActive(active);
	        video.setViews(views);  
	        video.setCategory(cateService.findById(categoryId));

	        String fname = "";
	        String uploadPath = "C:\\WebProgramming\\upload"; // Đường dẫn upload file
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }
	        
	        try {
	            Part part = req.getPart("images1");
	            if (part.getSize() > 0) {
	                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
	                int index = filename.lastIndexOf(".");
	                String ext = filename.substring(index + 1);
	                fname = System.currentTimeMillis() + "." + ext; // Tạo tên file mới để tránh trùng lặp

	                // Upload file
	                part.write(uploadPath + "/" + fname);
	                video.setPoster(fname); // Lưu tên file vào database
	            } else if (poster != null) {
	                video.setPoster(poster);
	            } else {
	                video.setPoster("avata.png"); // Nếu không có hình ảnh thì để mặc định
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	     // Gọi service để lưu dữ liệu
	        vidService.insert(video);

	        // Redirect về trang danh sách
	        resp.sendRedirect(req.getContextPath() + "/admin/videos");
	    	
	    }else if (url.contains("/admin/video/update")) {
	        String id = req.getParameter("videoId");
	        String title = req.getParameter("title");
	        String description = req.getParameter("description");
	        String poster = req.getParameter("poster");
	        
	        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
	        int active = Integer.parseInt(req.getParameter("active"));
	        int views = Integer.parseInt(req.getParameter("views"));
	        
	        // Lấy video cũ từ database
	        Video vidOld = vidService.findById(id);
	        String fileOld = vidOld.getPoster();

	        // Cập nhật dữ liệu video
	        Video video = new Video();
	        video.setVideoId(id);
	        video.setTitle(title);
	        video.setDescription(description);
	        video.setActive(active);
	        video.setViews(views);
	        video.setCategory(cateService.findById(categoryId));

	        String fname = "";
	        String uploadPath = Constants.DIR;
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }

	        try {
	            Part part = req.getPart("images1");
	            if (part != null && part.getSize() > 0) {
	                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
	                int index = filename.lastIndexOf(".");
	                String ext = filename.substring(index + 1);
	                fname = System.currentTimeMillis() + "." + ext;
	                
	                part.write(uploadPath + "/" + fname);
	                video.setPoster(fname);
	            } else if (poster != null && poster.startsWith("http")) {
	                video.setPoster(poster);  // Lưu lại link ảnh nếu người dùng cung cấp
	            } else {
	                video.setPoster(fileOld);  // Giữ hình ảnh cũ nếu không có thay đổi
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        // Cập nhật video trong cơ sở dữ liệu
	        vidService.update(video);

	        // Chuyển hướng lại trang danh sách video
	        resp.sendRedirect(req.getContextPath() + "/admin/videos");
	    }

	}
	
	
	

}
