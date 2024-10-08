package vn.iotstar.controllers.admin;

import java.io.File;



import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryService;
import vn.iotstar.utils.Constants;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/insert",
		"/admin/category/delete", "/admin/category/edit", "/admin/category/update" })
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();
		if (url.contains("/admin/category/add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);

		} else if (url.contains("/admin/categories")) {
			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		} else if (url.contains("/admin/category/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				cateService.delete(id);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		} else if (url.contains("/admin/category/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Category category = cateService.findById(id);
			req.setAttribute("cate", category);
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 	req.setCharacterEncoding("UTF-8");
		    resp.setCharacterEncoding("UTF-8");
		    String url = req.getRequestURI();
		    
		    if (url.contains("/admin/category/insert")) {
		        // Lấy dữ liệu từ form
		        String categoryname = req.getParameter("categoryname");
		        int status = Integer.parseInt(req.getParameter("status"));
		        String images = req.getParameter("images");

		        // Tạo đối tượng CategoryModel để lưu dữ liệu
		        Category category = new Category();
		        category.setCategoryname(categoryname);
		        category.setStatus(status);

		        // Xử lý upload file hình ảnh
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
		                category.setImages(fname); // Lưu tên file vào database
		            } else if (images != null) {
		                category.setImages(images);
		            } else {
		                category.setImages("avata.png"); // Nếu không có hình ảnh thì để mặc định
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        // Gọi service để lưu dữ liệu
		        cateService.insert(category);

		        // Redirect về trang danh sách
		        resp.sendRedirect(req.getContextPath() + "/admin/categories");

		    } else if (url.contains("/admin/category/update")) {
		        // Lấy dữ liệu từ form
		        String categoryname = req.getParameter("categoryname");
		        int status = Integer.parseInt(req.getParameter("status"));
		        String images = req.getParameter("images");
		        int id = Integer.parseInt(req.getParameter("categoryid")); 

		        // Lấy dữ liệu sản phẩm cũ từ database
		        Category cateOld = cateService.findById(id);
		        String fileOld = cateOld.getImages(); 

		        // Tạo đối tượng CategoryModel để cập nhật
		        Category category = new Category();
		        category.setCategoryname(categoryname);
		        category.setStatus(status);
		        category.setCategoryid(id); 

		       
		        String fname = "";
		        String uploadPath = Constants.DIR;
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
		                fname = System.currentTimeMillis() + "." + ext;

		                // Upload file
		                part.write(uploadPath + "/" + fname);

		                // Lưu tên file vào đối tượng
		                category.setImages(fname);
		            } else if (images == null || images.trim().isEmpty()) {
		                // Giữ hình ảnh cũ nếu không có hình mới
		                category.setImages(fileOld);
		            } else {
		                category.setImages(images);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        // Gọi service để cập nhật dữ liệu sản phẩm
		        cateService.update(category);

		        // Redirect về trang danh sách sản phẩm
		        resp.sendRedirect(req.getContextPath() + "/admin/categories");
		    }
	}

}
