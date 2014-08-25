/**
 * 网页文章Model
 */
package dswork.ep.model;
public class DsCmsPage
{
	//主键
	private Long id = 0L;
	//企业编码
	private String qybm = "";
	//栏目ID
	private Long categoryid = 0L;
	//标题
	private String title = "";
	//关键词
	private String keywords = "";
	//摘要
	private String summary = "";
	//内容
	private String content = "";
	//创建时间
	private String createtime = "";
	//图片
	private String img = "";
	//焦点图(0否，1是)
	private Integer imgtop = 0;
	//首页推荐(0否，1是)
	private Integer pagetop = 0;
	//网站模板
	private String viewsite = "";
	//APP模板
	private String viewapp = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getQybm()
	{
		return qybm;
	}

	public void setQybm(String qybm)
	{
		this.qybm = qybm;
	}

	public Long getCategoryid()
	{
		return categoryid;
	}

	public void setCategoryid(Long categoryid)
	{
		this.categoryid = categoryid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public Integer getImgtop()
	{
		return imgtop;
	}

	public void setImgtop(Integer imgtop)
	{
		this.imgtop = imgtop;
	}

	public Integer getPagetop()
	{
		return pagetop;
	}

	public void setPagetop(Integer pagetop)
	{
		this.pagetop = pagetop;
	}

	public String getViewsite()
	{
		return viewsite;
	}

	public void setViewsite(String viewsite)
	{
		this.viewsite = viewsite;
	}

	public String getViewapp()
	{
		return viewapp;
	}

	public void setViewapp(String viewapp)
	{
		this.viewapp = viewapp;
	}
}