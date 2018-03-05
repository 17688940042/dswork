/**
 * 用户类型资源Model
 */
package dswork.common.model;

public class DsCommonUsertypeRes
{
	// 资源对应的标识
	private String alias = "";
	// 资源对应的名称
	private String name = "";

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = String.valueOf(alias).trim();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = String.valueOf(name).trim();
	}
}
