package dswork.core.webio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import dswork.core.util.EnvironmentUtil;

/**
 * 临时文件上传服务类
 */
public class WebioUtil extends Thread
{
	// ################################################################################################
	// 基本配置参数
	// ################################################################################################
	private static final String toPathString(String key)
	{
		String s = EnvironmentUtil.getToString(key, "");
		String c = s.substring(s.length()-1);
		if(!c.equals("\\") && !c.equals("/"))
		{
			return s + "/";
		}
		return s;
	}

	/**
	 * 临时上传总目录
	 */
	public static final String PATH = WebioUtil.toPathString("webio.path");
	/**
	 * 文件占用最大空间（bit）
	 */
	public static final long MAXSIZE = EnvironmentUtil.getToLong("webio.maxsize", 20971520L);
	/**
	 * 默认允许上传的图片后缀
	 */
	public static final String IMAGE = EnvironmentUtil.getToString("webio.image", "jpg,jpeg,gif,png").toLowerCase();
	/**
	 * 默认允许上传的文件后缀
	 */
	public static final String FILE = EnvironmentUtil.getToString("webio.file", "jpg,jpeg,gif,png,bmp,doc,rtf,xls,txt,ppt,pdf,rar,zip,7z,docx,xlsx,pptx").toLowerCase();
	private static final String CHECK = "," + FILE + ",";

	
	// ################################################################################################
	// 文件操作相关
	// ################################################################################################
	/**
	 * 将一个文件读成byte(但需要注意文件太大时，内存是否足够)
	 * @param filePath 文件名称(全路径)
	 * @return 成功返回byte[]，失败返回null
	 */
	public static byte[] getToByte(String filePath)
	{
		try
		{
			if (filePath == null)
			{
				return null;
			}
			java.io.File file = new java.io.File(filePath);
			FileInputStream fin = new FileInputStream(file);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = fin.read(buffer)) != -1)// Read until EOF
			{
				bout.write(buffer, 0, bytes_read);
			}
			fin.close();
			bout.close();
			return bout.toByteArray();
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//删除文件或清空文件夹
	public static boolean delete(File file)
	{
		try
		{
			if(file.isDirectory())
			{
				String[] children = file.list();
				for(int i = 0; i < children.length; i++)
				{
					WebioUtil.delete(new java.io.File(file, children[i]));
				}
			}
			return file.delete();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 返回允许上传的后缀名字符串
	 * @param ext 需要过滤的上传文件后缀名，格式为"***,***"
	 * @return String
	 */
	public static final String getUploadExt(String ext)
	{
		if(ext == null)
		{
			return WebioUtil.FILE;//默认的后缀名字符串//jpg,jpeg,gif,png,bmp,doc,rtf,xls,txt,ppt,pdf,rar,zip,7z
		}
		ext = ext.trim().toLowerCase();
		if(ext.length() == 0 || ext.equals("file"))
		{
			return WebioUtil.FILE;//默认的后缀名字符串//jpg,jpeg,gif,png,bmp,doc,rtf,xls,txt,ppt,pdf,rar,zip,7z
		}
		else if(ext.equals("image") || ext.equals("img"))
		{
			return WebioUtil.IMAGE;//默认的图片后缀名字符串//jpg,jpeg,gif,png,bmp
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			String _tmp = sb.append(",").append(ext).append(",").toString();//前后加上","
			sb.setLength(0);
			String[] extarr = ext.split(",");
			for(String s : extarr)
			{
				sb.setLength(0);
				s = sb.append(",").append(s).append(",").toString();//前后加上","
				if(WebioUtil.CHECK.indexOf(s) == -1);//用户设置的上传后缀，但需要过滤掉不在默认的后缀名字符串中的后缀名
				{
					_tmp.replaceAll(s, ",");//移除非法后缀
				}
			}
			sb = null;
			_tmp = _tmp.substring(1, _tmp.length() - 1);//移除前后的","
			if(ext.length() == 0)
			{
				return WebioUtil.FILE;
			}
			return _tmp;
		}
	}
}
