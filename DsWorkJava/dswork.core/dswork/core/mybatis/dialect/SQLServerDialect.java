package dswork.core.mybatis.dialect;

/**
 * 可用于支持ROW_NUMBER() over(order by *)语法的数据库，如：SQLServer2005、SQLServer2008
 * @author skey
 */
public class SQLServerDialect extends Dialect
{
	/**
	 * 是否支持分页，limit和offset
	 * @return boolean
	 */
	public boolean supportsLimitOffset()
	{
		return true;
	}

	/**
	 * 将sql变成分页sql
	 * @param sql 原始sql语句
	 * @param offset 跳过行数
	 * @param limit 返回行数
	 * @return String
	 */
	public String getLimitString(String sql, int offset, int limit)
	{
		StringBuilder sb = new StringBuilder(sql.length() + 170);
		sb.append("select * from ( select ROW_NUMBER() over(order by _m.__temp__) rn, _m.* from (")
		.append("select 0 as __temp__, _t.* from (")
		.append(sql)
		.append(") _t ")
		.append(") _m ")
		.append(") _n where _n.rn > ").append(offset).append(" and _n.rn <= ").append(offset + limit);
		return sb.toString();
	}
}