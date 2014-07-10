<?xml version="1.0" encoding="UTF-8"?>
<!-- wadl_documentation.xsl (2008-12-09) An XSLT stylesheet for generating HTML documentation from WADL, by Mark Nottingham <mnot@yahoo-inc.com>. Copyright (c) 2006-2008 Yahoo! Inc. This work is licensed under the Creative Commons Attribution-ShareAlike 2.5 License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/2.5/ or send a letter to Creative Commons 543 Howard Street, 5th Floor San Francisco, California, 94105, USA -->
<!-- * FIXME - Doesn't inherit query/header params from resource/@type - XML schema import, include, redefine don't import -->
<!-- * TODO - forms - link to or include non-schema variable type defs (as a separate list?) - @href error handling -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:wadl="http://wadl.dev.java.net/2009/02" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:html="http://www.w3.org/1999/xhtml" xmlns:exsl="http://exslt.org/common" xmlns:date="http://exslt.org/dates-and-times" xmlns:ns="urn:namespace" xmlns:uid="xalan://java.util.UUID" xmlns:str="xalan://java.lang.String" extension-element-prefixes="exsl date" xmlns="http://www.w3.org/1999/xhtml" exclude-result-prefixes="xsl wadl xs html ns">
	<xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes" />
	<xsl:variable name="wadl-ns">
		http://wadl.dev.java.net/2009/02
	</xsl:variable>
	<!-- expand @hrefs, @types into a full tree -->
	<xsl:variable name="resources">
		<xsl:apply-templates select="/wadl:application/wadl:resources" mode="expand" />
	</xsl:variable>
	<xsl:template match="wadl:resources" mode="expand">
		<xsl:variable name="base">
			<xsl:choose>
				<xsl:when test="substring(@base, string-length(@base), 1) = '/'">
					<xsl:value-of select="substring(@base, 1, string-length(@base) - 1)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@base" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:element name="resources" namespace="{$wadl-ns}">
			<xsl:for-each select="namespace::*">
				<xsl:variable name="prefix" select="name(.)" />
				<xsl:if test="$prefix">
					<xsl:attribute name="ns:{$prefix}"><xsl:value-of select="." /></xsl:attribute>
				</xsl:if>
			</xsl:for-each>
			<xsl:apply-templates select="@*|node()" mode="expand">
				<xsl:with-param name="base" select="$base" />
			</xsl:apply-templates>
		</xsl:element>
	</xsl:template>
	<xsl:template match="wadl:resource[@type]" mode="expand" priority="1">
		<xsl:param name="base"></xsl:param>
		<xsl:variable name="uri" select="substring-before(@type, '#')" />
		<xsl:variable name="id" select="substring-after(@type, '#')" />
		<xsl:element name="resource" namespace="{$wadl-ns}">
			<xsl:attribute name="path"><xsl:value-of select="@path" /></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$uri">
					<xsl:variable name="included" select="document($uri, /)" />
					<xsl:copy-of select="$included/descendant::wadl:resource_type[@id=$id]/@*" />
					<xsl:attribute name="id"><xsl:value-of select="@type" />#<xsl:value-of select="@path" /></xsl:attribute>
					<xsl:apply-templates select="$included/descendant::wadl:resource_type[@id=$id]/*" mode="expand">
						<xsl:with-param name="base" select="$uri" />
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy-of select="//resource_type[@id=$id]/@*" />
					<xsl:attribute name="id"><xsl:value-of select="$base" />#<xsl:value-of select="@type" />#<xsl:value-of select="@path" /></xsl:attribute>
					<xsl:apply-templates select="//wadl:resource_type[@id=$id]/*" mode="expand">
						<xsl:with-param name="base" select="$base" />
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:apply-templates select="node()" mode="expand">
				<xsl:with-param name="base" select="$base" />
			</xsl:apply-templates>
		</xsl:element>
	</xsl:template>
	<xsl:template match="wadl:*[@href]" mode="expand">
		<xsl:param name="base"></xsl:param>
		<xsl:variable name="uri" select="substring-before(@href, '#')" />
		<xsl:variable name="id" select="substring-after(@href, '#')" />
		<xsl:element name="{local-name()}" namespace="{$wadl-ns}">
			<xsl:copy-of select="@*" />
			<xsl:choose>
				<xsl:when test="$uri">
					<xsl:attribute name="id"><xsl:value-of select="@href" /></xsl:attribute>
					<xsl:variable name="included" select="document($uri, /)" />
					<xsl:apply-templates select="$included/descendant::wadl:*[@id=$id]/*" mode="expand">
						<xsl:with-param name="base" select="$uri" />
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="id"><xsl:value-of select="$base" />#<xsl:value-of select="$id" /></xsl:attribute>
					<!-- xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute -->
					<xsl:attribute name="element"><xsl:value-of select="//wadl:*[@id=$id]/@element" /></xsl:attribute>
					<xsl:attribute name="mediaType"><xsl:value-of select="//wadl:*[@id=$id]/@mediaType" /></xsl:attribute>
					<xsl:attribute name="status"><xsl:value-of select="//wadl:*[@id=$id]/@status" /></xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of select="//wadl:*[@id=$id]/@name" /></xsl:attribute>
					<xsl:apply-templates select="//wadl:*[@id=$id]/*" mode="expand">
						<xsl:with-param name="base" select="$base" />
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<xsl:template match="node()[@id]" mode="expand">
		<xsl:param name="base"></xsl:param>
		<xsl:element name="{local-name()}" namespace="{$wadl-ns}">
			<xsl:copy-of select="@*" />
			<xsl:attribute name="id"><xsl:value-of select="$base" />#<xsl:value-of select="@id" /></xsl:attribute>
			<!-- xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute -->
			<xsl:apply-templates select="node()" mode="expand">
				<xsl:with-param name="base" select="$base" />
			</xsl:apply-templates>
		</xsl:element>
	</xsl:template>
	<xsl:template match="@*|node()" mode="expand">
		<xsl:param name="base"></xsl:param>
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" mode="expand">
				<xsl:with-param name="base" select="$base" />
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
	<!-- debug $resources <xsl:template match="/"> <xsl:copy-of select="$resources"/> </xsl:template> -->
	<!-- collect grammars (TODO: walk over $resources instead) -->
	<xsl:variable name="grammars">
		<xsl:copy-of select="/wadl:application/wadl:grammars/*[not(namespace-uri()=$wadl-ns)]" />
		<xsl:apply-templates select="/wadl:application/wadl:grammars/wadl:include[@href]" mode="include-grammar" />
		<xsl:apply-templates select="/wadl:application/wadl:resources/descendant::wadl:resource[@type]" mode="include-href" />
		<xsl:apply-templates select="exsl:node-set($resources)/descendant::wadl:*[@href]" mode="include-href" />
	</xsl:variable>
	<xsl:template match="wadl:include[@href]" mode="include-grammar">
		<xsl:variable name="included" select="document(@href, /)/*"></xsl:variable>
		<xsl:element name="wadl:include">
			<xsl:attribute name="href"><xsl:value-of select="@href" /></xsl:attribute>
			<xsl:copy-of select="$included" /> <!-- FIXME: xml-schema includes, etc -->
		</xsl:element>
	</xsl:template>
	<xsl:template match="wadl:*[@href]" mode="include-href">
		<xsl:variable name="uri" select="substring-before(@href, '#')" />
		<xsl:if test="$uri">
			<xsl:variable name="included" select="document($uri, /)" />
			<xsl:copy-of select="$included/wadl:application/wadl:grammars/*[not(namespace-uri()=$wadl-ns)]" />
			<xsl:apply-templates select="$included/descendant::wadl:include[@href]" mode="include-grammar" />
			<xsl:apply-templates select="$included/wadl:application/wadl:resources/descendant::wadl:resource[@type]" mode="include-href" />
			<xsl:apply-templates select="$included/wadl:application/wadl:resources/descendant::wadl:*[@href]" mode="include-href" />
		</xsl:if>
	</xsl:template>
	<xsl:template match="wadl:resource[@type]" mode="include-href">
		<xsl:variable name="uri" select="substring-before(@type, '#')" />
		<xsl:if test="$uri">
			<xsl:variable name="included" select="document($uri, /)" />
			<xsl:copy-of select="$included/wadl:application/wadl:grammars/*[not(namespace-uri()=$wadl-ns)]" />
			<xsl:apply-templates select="$included/descendant::wadl:include[@href]" mode="include-grammar" />
			<xsl:apply-templates select="$included/wadl:application/wadl:resources/descendant::wadl:resource[@type]" mode="include-href" />
			<xsl:apply-templates select="$included/wadl:application/wadl:resources/descendant::wadl:*[@href]" mode="include-href" />
		</xsl:if>
	</xsl:template>
	<!-- main template -->
	<xsl:template match="/wadl:application">
		<html lang="en">
			<head>
				<title>
					<xsl:choose>
						<xsl:when test="wadl:doc[@title]">
							<xsl:value-of select="wadl:doc[@title][1]/@title" />
						</xsl:when>
						<xsl:otherwise>
							My Web Application
						</xsl:otherwise>
					</xsl:choose>
				</title>
				<link rel="icon" href="../../fav.ico" />
				<script src="../../js/jquery.js">//</script>
				<script src="../../js/bootstrap.js">//</script>
				<script src="../../js/prettify.js">//</script>
				<script src="../../js/vkbeautify.js">//</script>
				<script src="../../js/local.js">//</script>
				<!-- This variable is replaced by Maven's resource filtering to be true or false -->
				<xsl:variable name="development" select="${development}()" />
				<xsl:choose>
					<xsl:when test="$development">
						<link href="../../css/api.less" rel="stylesheet/less" type="text/css" />
						<script src="../../js/less.js">//</script>
					</xsl:when>
					<xsl:otherwise>
						<link href="../../css/api.css" rel="stylesheet" type="text/css" />
					</xsl:otherwise>
				</xsl:choose>
				<!-- These need to be in comments otherwise XSL strips them out -->
				<xsl:comment><![CDATA[[if IE 6]>
					<link href="../../css/ie6.css" rel="stylesheet" />
					<script src="../../js/ie6.js"></script>
				<![endif]]]></xsl:comment>
				<!-- These need to be in comments otherwise XSL strips them out -->
				<xsl:comment><![CDATA[[if lt IE 9]>
					<script src="../../js/html5.js"></script>
				<![endif]]]></xsl:comment>
			</head>
			<body data-spy="scroll" data-target="nav">
				<nav>
					<ul>
						<li>
							<a href="#resources">
								<i class="icon-chevron-right">
									<span>.</span>
								</i>
								Resources
							</a>
							<ul>
								<xsl:apply-templates select="wadl:resources/wadl:resource" mode="toc">
									<xsl:sort select="@path" />
								</xsl:apply-templates>
							</ul>
						</li>
					</ul>
					<ul>
						<li>
							<a href="#types">
								<i class="icon-chevron-right">
									<span>.</span>
								</i>
								Types
							</a>
							<ul>
								<xsl:apply-templates select="wadl:grammars/xs:schema/xs:complexType" mode="toc">
									<xsl:sort select="@name" />
								</xsl:apply-templates>
							</ul>
						</li>
					</ul>
					<ul>
						<li>
							<a href="#constants">
								<i class="icon-chevron-right">
									<span>.</span>
								</i>
								Constants
							</a>
							<ul>
								<xsl:apply-templates select="wadl:grammars/xs:schema/xs:simpleType" mode="toc">
									<xsl:sort select="@name" />
								</xsl:apply-templates>
							</ul>
						</li>
						<!-- <li> <a href="#faults"> <i class="icon-chevron-right"> <span>.</span> </i> Faults </a> <ul class="hide"> <xsl:apply-templates select="exsl:node-set($resources)/descendant::wadl:fault" mode="toc" /> </ul> </li> -->
					</ul>
				</nav>
				<article class="api">
					<header>
						<h1>
							<a class="brand" href="../../">
								LLoyds Banking Group
								<small>REST API v1</small>
							</a>
						</h1>
					</header>
					<article id="introduction">
						<h2>Introduction</h2>
						<h3>Response Categories</h3>		
						<table class="table table-striped table-hover">
							<tr>
								<th>Type</th>
								<th>Description</th>
								<th>App behaviour</th>
								<th>Example</th>
								<th>Error Status</th>
							</tr>
							<tr>
								<td>Success</td>	
								<td>Happy path (journey success page)</td>	
								<td>App simply needs to receive the message and display on relevant outcome page</td>	
								<td>Payment/ transfer success messages - faster, standard, immediate, future etc</td>
								<td>NA</td>
							</tr>						
							<tr>
								<td>Failed Outcome</td>
								<td>Unhappy path (journey failed page)</td>
								<td>App simply needs to receive the message and display on relevant outcome page</td>	
								<td>Non-fraud related failures e.g. transfer/ payment failed, EIA unsuccessful, new beneficiary failed, no mandate to log in</td>
								<td>FAILED_OUTCOME</td>	
							</tr>							
							<tr>
								<td>Bad request</td>
								<td>Unhappy path (journey incomplete, user required to re-enter/ re-complete step)</td>
								<td>User held on the same page, unable to proceed, with error shown</td>
								<td>Inputs incorrect e.g. credentials (password, MI), payment details (sort code, acc number etc)</td>
								<td>BAD_REQUEST</td>
							</tr>							
							<tr>
								<td>Forced logout</td>
								<td>User logged out/ unable to logon for security reasons/ unknown exception</td>
								<td>Error logged out</td>
								<td>Account locked, mandate suspended, no 2FA users, fraud suspected payment declined</td>
								<td>FORCED_LOGOUT</td>
							</tr>						
							<tr>
								<td>Error try again</td>
								<td>Checked and unchecked exceptions (EA components)	Error logged in (if authenticated)/ error logged out (if not authenticated)	Generic recoverable error</td>
								<td>Navigate to generic error page</td>
								<td>Generic recoverable errors</td>
								<td>TRY_AGAIN</td>
							</tr>							
						</table>					
						<h3>HTTP verb overriding</h3>
						<p>All requests below may be sent to the server using the POST verb. If you do this, you should include a _method parameter which contains the required method. E.g.</p>
						<pre><code>POST /foo/bar?baz=qux
_method=DELETE</code></pre>
						<p>is equivalent to sending:</p>
						<pre><code>DELETE /foo/bar?baz=qux</code></pre>
						<h4>HTTP verb overriding and parameters</h4>
						<p>Parameters required as part of the query string should still be sent as part of the query string. E.g.</p>
						<pre><code>POST /foo/{bar}?baz=qux
_method=GET&amp;bar=warble</code></pre>
						<p>and</p>
						<pre><code>POST /foo/{bar}
_method=GET&amp;bar=warble&amp;baz=qux</code></pre>
						<p>are not equivalent.</p>
						<h3>Path placeholders</h3>
						<p>The API contains several placeholders in the URLs, such as <code>/foo/{bar}</code>.</p>
						<p>As we cannot accept sensitive information as part of our URLs, these should be sent as POST parameters using verb overriding as described above.  E.g.</p>
						<pre><code>POST /foo/{bar}/baz/{qux}?waldo=fred
bar=hello&amp;qux=world&amp;_method=PUT</code></pre>
						<p>..is equivalent to sending:</p>
						<pre><code>PUT /foo/hello/baz/world?waldo=fred</code></pre>
						<p>N.b. It is necessary to send the _method parameter for the path placeholder functionality to trigger.</p>
					</article>
					<article id="resources">
						<h2>Resources</h2>
						<xsl:apply-templates select="wadl:resources/wadl:resource" mode="list">
							<xsl:sort select="@path" />
						</xsl:apply-templates>
					</article>
					<article id="types">
						<h2>Types</h2>
						<xsl:apply-templates select="wadl:grammars/xs:schema/xs:complexType" mode="list">
							<xsl:sort select="@name" />
						</xsl:apply-templates>
					</article>
					<article id="constants">
						<h2>Constants</h2>
						<xsl:apply-templates select="wadl:grammars/xs:schema/xs:simpleType" mode="list">
							<xsl:sort select="@name" />
						</xsl:apply-templates>
					</article>
					<!-- <article id="faults"> <h2>Faults</h2> <xsl:apply-templates select="exsl:node-set($resources)/descendant::wadl:fault" mode="list" /> </article> -->
					<footer>
						<small>
							&#169; Lloyds Banking Group
							<xsl:value-of select="date:year()" />
							. Documentation generated from the
							<a href="../../api/v1?_wadl">wadl</a>
							file.
						</small>
					</footer>
				</article>
			</body>
		</html>
	</xsl:template>
	<!-- Formats contract URLs in left hand nav table -->
	<xsl:template match="wadl:resource" mode="toc">
		<xsl:param name="context" />
		<xsl:variable name="id">
			<xsl:call-template name="get-id" />
		</xsl:variable>
		<xsl:variable name="name">
			<xsl:value-of select="$context" />
			/
			<xsl:value-of select="@path" />
		</xsl:variable>
		<li>
			<a href="#{translate(translate(translate(translate(normalize-space($name), ' ', ''), '/', '_'), '{', ''), '}', '')}">
				<i class="icon-chevron-right">
					<span>.</span>
				</i>
				<xsl:value-of select="translate(normalize-space($name), ' ', '')" />
			</a>
			<xsl:if test="./wadl:resource">
				<ul>
					<xsl:apply-templates select="./wadl:resource" mode="toc">
						<xsl:with-param name="context" select="$name" />
						<xsl:sort select="@path" />
					</xsl:apply-templates>
				</ul>
			</xsl:if>
		</li>
	</xsl:template>
	<!-- Formats types in left hand nav table -->
	<xsl:template match="xs:complexType" mode="toc">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<li>
			<a href="#{$name}">
				<i class="icon-chevron-right">
					<span>.</span>
				</i>
				<xsl:value-of select="translate(normalize-space($name), ' ', '')" />
			</a>
		</li>
	</xsl:template>
	<!-- Formats enums in left hand nav table -->
	<xsl:template match="xs:simpleType" mode="toc">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<li>
			<a href="#{$name}">
				<i class="icon-chevron-right">
					<span>.</span>
				</i>
				<xsl:value-of select="translate(normalize-space($name), ' ', '')" />
			</a>
		</li>
	</xsl:template>
	<!-- Formats faults in left hand nav table -->
	<xsl:template match="wadl:representation|wadl:fault" mode="toc">
		<xsl:variable name="id">
			<xsl:call-template name="get-id" />
		</xsl:variable>
		<xsl:variable name="href" select="@id" />
		<xsl:choose>
			<xsl:when test="preceding::wadl:*[@id=$href]" />
			<xsl:otherwise>
				<li>
					<a href="#{$id}">
						<xsl:call-template name="representation-name" />
					</a>
				</li>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- Listings -->
	<xsl:template match="wadl:resources" mode="list">
		<xsl:variable name="base">
			<xsl:choose>
				<xsl:when test="substring(@base, string-length(@base), 1) = '/'">
					<xsl:value-of select="substring(@base, 1, string-length(@base) - 1)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@base" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:apply-templates select="wadl:resource" mode="list" />
	</xsl:template>
	<xsl:template match="wadl:resource" mode="list">
		<xsl:param name="context" />
		<xsl:variable name="href" select="@id" />
		<xsl:choose>
			<xsl:when test="preceding::wadl:resource[@id=$href]" />
			<xsl:otherwise>
				<xsl:variable name="id">
					<xsl:call-template name="get-id" />
				</xsl:variable>
				<xsl:variable name="name">
					<xsl:value-of select="$context" />
					/
					<xsl:value-of select="@path" />
					<xsl:for-each select="wadl:param[@style='matrix']">
						<span class="optional">
							;
							<xsl:value-of select="@name" />
							=...
						</span>
					</xsl:for-each>
				</xsl:variable>
				<div class="resource listItem" id="{translate(translate(translate(translate(normalize-space($name), ' ', ''), '/', '_'), '{', ''), '}', '')}">
					<xsl:variable name="resourceUrl">
						<xsl:choose>
							<xsl:when test="wadl:doc[@title]">
								<xsl:value-of select="wadl:doc[@title][1]/@title" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:copy-of select="$name" />
								<xsl:for-each select="wadl:method[1]/wadl:request/wadl:param[@style='query']">
									<xsl:choose>
										<xsl:when test="@required='true'">
											<xsl:choose>
												<xsl:when test="preceding-sibling::wadl:param[@style='query']">
													&amp; </xsl:when>
												<xsl:otherwise>
													?
												</xsl:otherwise>
											</xsl:choose>
											<xsl:value-of select="@name" />
										</xsl:when>
										<xsl:otherwise>
											<span class="optional">
												<xsl:choose>
													<xsl:when test="preceding-sibling::wadl:param[@style='query']">
														&amp; </xsl:when>
													<xsl:otherwise>
														?
													</xsl:otherwise>
												</xsl:choose>
												<xsl:value-of select="@name" />
											</span>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="resourceUrl">
						<xsl:value-of select="translate(normalize-space($resourceUrl), ' ', '')" />
					</xsl:variable>
					<h3>
						<xsl:value-of select="$resourceUrl" />
					</h3>
					<xsl:apply-templates select="wadl:doc" />
					<xsl:apply-templates select="." mode="param-group">
						<xsl:with-param name="prefix">
							resource-wide
						</xsl:with-param>
						<xsl:with-param name="style">
							template
						</xsl:with-param>
					</xsl:apply-templates>
					<xsl:apply-templates select="." mode="param-group">
						<xsl:with-param name="prefix">
							resource-wide
						</xsl:with-param>
						<xsl:with-param name="style">
							matrix
						</xsl:with-param>
					</xsl:apply-templates>
					<!-- If there are path parameters, display them in a table -->
					<xsl:if test="wadl:param">
						<div class="pathParamters">
							<h4>Path parameters</h4>
							<table class="table table-striped table-hover">
								<thead>
									<tr>
										<th>Name</th>
										<th>Type</th>
										<th>Notes</th>
										<th>Constraints</th>
									</tr>
								</thead>
								<tbody>
									<xsl:apply-templates select="wadl:param" />
								</tbody>
							</table>
						</div>
					</xsl:if>
					<xsl:apply-templates select="wadl:method">
						<xsl:with-param name="resourceUrl" select="$resourceUrl" />
					</xsl:apply-templates>
				</div>
				<!-- Apply template to sub-resources -->
				<xsl:apply-templates select="wadl:resource" mode="list">
					<xsl:with-param name="context" select="$name" />
				</xsl:apply-templates>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="string-replace-all">
		<xsl:param name="text" />
		<xsl:param name="replace" />
		<xsl:param name="by" />
		<xsl:choose>
			<xsl:when test="contains($text, $replace)">
				<xsl:value-of select="substring-before($text,$replace)" />
				<xsl:value-of select="$by" />
				<xsl:call-template name="string-replace-all">
					<xsl:with-param name="text" select="substring-after($text,$replace)" />
					<xsl:with-param name="replace" select="$replace" />
					<xsl:with-param name="by" select="$by" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="make-url-safe">
		<xsl:param name="text" />
		<xsl:variable name="texta">
			<xsl:call-template name="string-replace-all">
				<xsl:with-param name="text" select="$text" />
				<xsl:with-param name="replace" select="'{'" />
				<xsl:with-param name="by" select="'%7B'" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="textb">
			<xsl:call-template name="string-replace-all">
				<xsl:with-param name="text" select="$texta" />
				<xsl:with-param name="replace" select="'}'" />
				<xsl:with-param name="by" select="'%7D'" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:value-of select="$textb" />
	</xsl:template>
	<xsl:template match="wadl:method">
		<xsl:param name="resourceUrl" />
		<xsl:variable name="id">
			<xsl:call-template name="get-id" />
		</xsl:variable>
		<div class="method">
			<xsl:if test="./wadl:doc/wadl:meta[@name='draft' and @content='true']">
				<div class="alert alert-warning">
					<strong>Draft API method</strong>
					This API method is a draft and has not been finalised - it's arguments, location and return types may change.
				</div>
			</xsl:if>
			<h4 id="{$id}">
				<xsl:value-of select="@name" />&#160;<xsl:value-of select="$resourceUrl" />
			</h4>
			<xsl:apply-templates select="wadl:doc" />
			<xsl:apply-templates select="wadl:request" />
			<!-- If there are path parameters, display them in a table -->
			<xsl:if test="wadl:request/wadl:param[@style='query']">
				<h5>Query parameters</h5>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Notes</th>
							<th>Constraints</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="wadl:request/wadl:param[@style='query']" />
					</tbody>
				</table>
			</xsl:if>
			<xsl:if test="wadl:request/wadl:param[@style='header']">
				<h5>Header parameters</h5>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Notes</th>
							<th>Constraints</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="wadl:request/wadl:param[@style='header']" />
					</tbody>
				</table>
			</xsl:if>	
			<xsl:if test="wadl:response">
				<h5 class="response">Response</h5>
				
				<xsl:if test="wadl:doc">
					<xsl:apply-templates select="wadl:response/wadl:doc" />
				</xsl:if>
				
				<table class="table">
					<thead>
						<tr>
							<th>Result</th>
							<th>Code</th>
							<th>Return type</th>
							<th>Category</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="wadl:response" mode="responseTable" />
					</tbody>
				</table>
			</xsl:if>
			<xsl:if test="./wadl:response/wadl:representation">
				<xsl:variable name="methodType" select="@name" />
				<div class="representation">
					<h5>Available response representations:</h5>
					<ul>
						<xsl:variable name="requestUrl">
							../../api/v1
							<xsl:call-template name="make-url-safe">
								<xsl:with-param name="text" select="$resourceUrl" />
							</xsl:call-template>
						</xsl:variable>
						<xsl:variable name="requestUrl" select="translate(normalize-space($requestUrl), ' ', '')" />
						<xsl:if test="./wadl:response/wadl:representation[@mediaType='application/json']">
							<!-- If the request url is if the form foo?bar=baz, make it foo.json -->
							<xsl:variable name="jsonRequestUrl">
								<xsl:choose>
									<xsl:when test="contains($requestUrl, '?')">
										<xsl:value-of select="str:replaceAll($requestUrl, '\?.*', '.json')" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$requestUrl" />
										.json
									</xsl:otherwise>
								</xsl:choose>
								?_method=
								<xsl:value-of select="@name" />
							</xsl:variable>
							<!-- Strip spaces.. -->
							<xsl:variable name="jsonRequestUrl" select="translate(normalize-space($jsonRequestUrl), ' ', '')" />
							<li>
								<xsl:variable name="jsonId" select="uid:randomUUID()" />
								<a class="{$methodType} responseLink" href="{$jsonRequestUrl}">
									<xsl:value-of select="$resourceUrl" />
								</a>
								(application/json) (
								<a href="{$jsonRequestUrl}">raw</a>
								)
								<pre class="prettyprint linenums language-js hide code {$jsonId}">
									<xsl:comment></xsl:comment>
								</pre>
							</li>
						</xsl:if>
						<xsl:if test="./wadl:response/wadl:representation[@mediaType='application/xml']">
							<!-- If the request url is if the form foo?bar=baz, make it foo.xml -->
							<xsl:variable name="xmlRequestUrl">
								<xsl:choose>
									<xsl:when test="contains($requestUrl, '?')">
										<xsl:value-of select="str:replaceAll($requestUrl, '\?.*', '.xml?')" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$requestUrl" />
										.xml
									</xsl:otherwise>
								</xsl:choose>
								?_method=
								<xsl:value-of select="@name" />
							</xsl:variable>
							<!-- Strip spaces.. -->
							<xsl:variable name="xmlRequestUrl" select="translate(normalize-space($xmlRequestUrl), ' ', '')" />
							<li>
								<xsl:variable name="xmlId" select="uid:randomUUID()" />
								<a class="{$methodType} responseLink" href="{$xmlRequestUrl}">
									<xsl:value-of select="$resourceUrl" />
								</a>
								(application/xml) (
								<a href="{$xmlRequestUrl}">raw</a>
								)
								<pre class="prettyprint linenums language-xml hide code {$xmlId}">
									<xsl:comment></xsl:comment>
								</pre>
							</li>
						</xsl:if>
						<xsl:if test="./wadl:response/wadl:representation[@mediaType='text/html']">
							<!-- If the request url is if the form foo?bar=baz, make it foo.html -->
							<xsl:variable name="htmlRequestUrl">
								<xsl:choose>
									<xsl:when test="contains($requestUrl, '?')">
										<xsl:value-of select="str:replaceAll($requestUrl, '\?.*', '.html?')" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$requestUrl" />
										.html
									</xsl:otherwise>
								</xsl:choose>
								?_method=
								<xsl:value-of select="@name" />
							</xsl:variable>
							<!-- Strip spaces.. -->
							<xsl:variable name="htmlRequestUrl" select="translate(normalize-space($htmlRequestUrl), ' ', '')" />
							<li>
								<xsl:variable name="htmlId" select="uid:randomUUID()" />
								<a class="{$methodType} responseLink" href="{$htmlRequestUrl}">
									<xsl:value-of select="$resourceUrl" />
								</a>
								(text/html) (
								<a href="{$htmlRequestUrl}">raw</a>
								)
								<pre class="prettyprint linenums language-html hide code {$htmlId}">
									<xsl:comment></xsl:comment>
								</pre>
							</li>
						</xsl:if>
						<xsl:if test="./wadl:response/wadl:representation[@mediaType='text/plain']">
							<!-- If the request url is if the form foo?bar=baz, make it foo.txt -->
							<xsl:variable name="textRequestUrl">
								<xsl:choose>
									<xsl:when test="contains($requestUrl, '?')">
										<xsl:value-of select="str:replaceAll($requestUrl, '\?.*', '.txt?')" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$requestUrl" />
										.txt
									</xsl:otherwise>
								</xsl:choose>
								?_method=
								<xsl:value-of select="@name" />
							</xsl:variable>
							<!-- Strip spaces.. -->
							<xsl:variable name="textRequestUrl" select="translate(normalize-space($textRequestUrl), ' ', '')" />
							<li>
								<xsl:variable name="textId" select="uid:randomUUID()" />
								<a class="{$methodType} responseLink" href="{$textRequestUrl}">
									<xsl:value-of select="$resourceUrl" />
								</a>
								(text/plain) (
								<a href="{$textRequestUrl}">raw</a>
								)
								<pre class="prettyprint linenums language-html hide code {$textId}">
									<xsl:comment></xsl:comment>
								</pre>
							</li>
						</xsl:if>
					</ul>
				</div>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template match="wadl:request">
		<xsl:apply-templates select="." mode="param-group">
			<xsl:with-param name="prefix">
				request
			</xsl:with-param>
			<xsl:with-param name="style">
				query
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:apply-templates select="." mode="param-group">
			<xsl:with-param name="prefix">
				request
			</xsl:with-param>
			<xsl:with-param name="style">
				header
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:if test="wadl:doc">
			<h5 class="request">Request</h5>
			<xsl:apply-templates select="wadl:doc" />
		</xsl:if>
		<xsl:if test="wadl:representation">
			<div class="representation">
				<h5>Acceptable request representations:</h5>
				<ul>
					<xsl:apply-templates select="wadl:representation" />
				</ul>
			</div>
		</xsl:if>
	</xsl:template>
	<xsl:template match="wadl:response">
		<xsl:apply-templates select="." mode="param-group">
			<xsl:with-param name="prefix">
				response
			</xsl:with-param>
			<xsl:with-param name="style">
				header
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:if test="wadl:doc">
			<h5 class="response">Response</h5>
			<xsl:apply-templates select="wadl:doc[not(@type)]" />
			<table class="table">
				<thead>
					<tr>
						<th>Result</th>
						<th>Code</th>
						<th>Return type</th>
						<th>Category</th>
					</tr>
				</thead>
				<tbody>
					<xsl:apply-templates select="wadl:doc[@type='statusCodes']" />
				</tbody>
			</table>
		</xsl:if>
		<xsl:if test="wadl:fault">
			<p>
				<em>potential faults:</em>
			</p>
			<ul>
				<xsl:apply-templates select="wadl:fault" />
			</ul>
		</xsl:if>
	</xsl:template>
	<xsl:template match="wadl:response" mode="responseTable">
		<xsl:variable name="statusCode" select="@status" />
		<xsl:variable name="category" select="@category" />
		<xsl:variable name="className">
			<xsl:choose>
				<xsl:when test="starts-with($statusCode, '2')">
					success
				</xsl:when>
				<xsl:otherwise>
					error
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="message">
			<xsl:choose>
				<xsl:when test="starts-with($statusCode, '200') or starts-with($statusCode, '201') or starts-with($statusCode, '204')">
					Success
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="wadl:doc/wadl:meta[@name='responseMessage']/@content" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<tr class="{$className}">
			<td>
				<xsl:value-of select="$message" />
			</td>
			<td>
				<a href="http://httpstatus.es/{$statusCode}">
					<xsl:value-of select="$statusCode" />
				</a>
			</td>
			<td>
				<xsl:choose>
					<xsl:when test="wadl:doc/wadl:meta[@name='responseType']/@content">
						<xsl:variable name="responseType" select="wadl:doc/wadl:meta[@name='responseType']/@content" />
						<a href="#{$responseType}">
							<xsl:value-of select="$responseType" />
						</a>
					</xsl:when>
					<xsl:when test="starts-with($statusCode, '4') or starts-with($statusCode, '5') or starts-with($statusCode, '202') ">
						<a href="#error">error</a>
					</xsl:when>
					<xsl:otherwise>
						-
					</xsl:otherwise>
				</xsl:choose>
			</td>
			<td>
				<xsl:choose>
					<xsl:when test="string-length($category) &gt; 0">
						<xsl:value-of select="$category" />
					</xsl:when>
					<xsl:otherwise>
						-
					</xsl:otherwise>
				</xsl:choose>
			</td>
		</tr>
	</xsl:template>
	<!-- Styles individual request repsentations -->
	<xsl:template match="wadl:representation|wadl:fault">
		<xsl:variable name="id">
			<xsl:call-template name="get-id" />
		</xsl:variable>
		<li>
			<xsl:variable name="className" select="." />
			<xsl:if test="@mediaType='application/json'">
				<a class="responseLink" href="../../viewRequestObject.json?class={$className}">
					<xsl:value-of select="@mediaType" />
				</a>
				(
				<a href="../../viewRequestObject.json?class={$className}">raw</a>
				)
				<pre class="prettyprint linenums language-js hide code">
					<xsl:comment></xsl:comment>
				</pre>
			</xsl:if>
			<xsl:if test="@mediaType='application/xml'">
				<a class="responseLink" href="../../viewRequestObject.xml?class={$className}">
					<xsl:value-of select="@mediaType" />
				</a>
				(
				<a href="../../viewRequestObject.xml?class={$className}">raw</a>
				)
				<pre class="prettyprint linenums language-xml hide code">
					<xsl:comment></xsl:comment>
				</pre>
			</xsl:if>
			<xsl:if test="@mediaType='application/x-www-form-urlencoded'">
				<p>application/x-www-form-urlencoded</p>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Notes</th>
							<th>Constraints</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="wadl:param" />
					</tbody>
				</table>
			</xsl:if>
		</li>
	</xsl:template>
	<xsl:template match="xs:complexType" mode="childTypeList">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<li>
			<a href="#{$name}">
				<xsl:value-of select="@name" />
			</a>
		</li>
		<xsl:apply-templates select="//xs:complexType[./xs:complexContent/xs:extension[@base=$name]]" mode="childTypeList" />
	</xsl:template>
	<!-- Formats types in main list -->
	<xsl:template match="xs:complexType" mode="list">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<div class="type listItem" id="{$name}">
			<h3>
				<xsl:value-of select="$name" />
			</h3>
			<xsl:if test="xs:complexContent/xs:extension|xs:sequence/xs:element">
				<div class="properties">
				<xsl:apply-templates select="xs:complexContent/xs:extension" mode="propertyList" />
				<xsl:if test="xs:sequence/xs:element">
					<ul>
						<xsl:apply-templates select="xs:sequence/xs:element" mode="propertyList" />
					</ul>
				</xsl:if>
			</div>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="@abstract='true'">
					<div class="representation">
						<h5>Sub-types</h5>
						<ul>
							<!-- Match any complexType that extends our element -->
							<xsl:apply-templates select="//xs:complexType[./xs:complexContent/xs:extension[@base=$name]]" mode="childTypeList" />
						</ul>
					</div>
				</xsl:when>
				<xsl:otherwise>
					<div class="representation">
						<h5>Availabile representations</h5>
						<ul>
							<li>
								<a class="responseLink" href="../../viewTypeObject.json?class={$name}">application/json</a>
								(
								<a href="../../viewTypeObject.json?class={$name}">raw</a>
								)
								<pre class="prettyprint linenums language-js hide code">
									<xsl:comment></xsl:comment>
								</pre>
							</li>
							<li>
								<a class="responseLink" href="../../viewTypeObject.xml?class={$name}">application/xml</a>
								(
								<a href="../../viewTypeObject.xml?class={$name}">raw</a>
								)
								<pre class="prettyprint linenums language-xml hide code">
									<xsl:comment></xsl:comment>
								</pre>
							</li>
						</ul>
					</div>
				</xsl:otherwise>
			</xsl:choose>
		</div>
	</xsl:template>
	<!-- Formats enums in main list -->
	<xsl:template match="xs:simpleType" mode="list">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<div class="type listItem" id="{$name}">
			<h3>
				<xsl:value-of select="$name" />
			</h3>
			<div class="properties">
				<ul>
					<xsl:variable name="type" select="xs:restriction/@base" />
					<xsl:variable name="localname" select="substring-after($type, ':')" />
					<xsl:for-each select="xs:restriction/xs:enumeration">
						<li>
							<xsl:value-of select="@value" />
							:
							<a href="http://www.w3.org/TR/xmlschema-2/#{$localname}">
								<xsl:value-of select="$localname" />
							</a>
						</li>
					</xsl:for-each>
				</ul>
			</div>
		</div>
	</xsl:template>
	<!-- Formats properties of types in main list -->
	<xsl:template match="xs:element" mode="propertyList">
		<xsl:param name="inheritedFrom" />
		<li>
			<xsl:if test="@ref">
				<xsl:value-of select="@ref" />
				:
				<!-- This will link custom types (e.g. transaction, account) -->
				<xsl:call-template name="link-qname">
					<xsl:with-param name="qname" select="@ref" />
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="@name">
				<xsl:value-of select="@name" />
				:
				<xsl:call-template name="link-qname">
					<xsl:with-param name="qname" select="@type" />
				</xsl:call-template>
				<xsl:if test="xs:complexType/xs:sequence/xs:element">
					<!-- This will link built in types (e.g. int, string) -->
					<xsl:call-template name="link-qname">
						<xsl:with-param name="qname" select="xs:complexType/xs:sequence/xs:element/@type" />
					</xsl:call-template>
					<!-- This will link custom types (e.g. transaction, account) -->
					<xsl:call-template name="link-qname">
						<xsl:with-param name="qname" select="xs:complexType/xs:sequence/xs:element/@ref" />
					</xsl:call-template>
					[ ]
				</xsl:if>
			</xsl:if>
		</li>
	</xsl:template>
	<!-- Gets properties from type parent in main list -->
	<xsl:template match="xs:extension" mode="propertyList">
		<xsl:param name="inheritedFrom" />
		<xsl:variable name="base">
			<xsl:value-of select="@base" />
		</xsl:variable>
		<xsl:apply-templates select="//xs:complexType[@name=$base]" mode="extendingList" />
		<xsl:if test="xs:sequence/xs:element">
			<ul>
				<xsl:if test="$inheritedFrom">
					<small>
						(Inherited from
						<xsl:call-template name="link-qname">
							<xsl:with-param name="qname" select="$inheritedFrom" />
						</xsl:call-template>
						)
					</small>
				</xsl:if>
				<xsl:apply-templates select="xs:sequence/xs:element" mode="propertyList">
					<xsl:with-param select="$inheritedFrom" name="inheritedFrom" />
				</xsl:apply-templates>
			</ul>
		</xsl:if>
	</xsl:template>
	<!-- Formats properties of types in main list -->
	<xsl:template match="xs:complexType" mode="extendingList">
		<xsl:apply-templates select="xs:complexContent/xs:extension" mode="propertyList">
			<xsl:with-param select="@name" name="inheritedFrom" />
		</xsl:apply-templates>
		<xsl:if test="xs:sequence/xs:element">
			<ul>
				<small>
					(Inherited from
					<xsl:call-template name="link-qname">
						<xsl:with-param name="qname" select="@name" />
					</xsl:call-template>
					)
				</small>
				<xsl:apply-templates select="xs:sequence/xs:element" mode="propertyList" />
			</ul>
		</xsl:if>
	</xsl:template>
	<!-- Formats faults in main list -->
	<xsl:template match="wadl:representation|wadl:fault" mode="list">
		<xsl:variable name="id">
			<xsl:call-template name="get-id" />
		</xsl:variable>
		<xsl:variable name="href" select="@id" />
		<xsl:variable name="expanded-name">
			<xsl:call-template name="expand-qname">
				<xsl:with-param select="@element" name="qname" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="preceding::wadl:*[@id=$href]" />
			<xsl:otherwise>
				<h3 id="{$id}">
					<xsl:call-template name="representation-name" />
				</h3>
				<xsl:apply-templates select="wadl:doc" />
				<xsl:if test="@element or wadl:param">
					<div class="representation">
						<xsl:if test="@element">
							<h5>XML Schema</h5>
							<xsl:call-template name="get-element">
								<xsl:with-param name="context" select="." />
								<xsl:with-param name="qname" select="@element" />
							</xsl:call-template>
						</xsl:if>
						<xsl:apply-templates select="." mode="param-group">
							<xsl:with-param name="style">
								plain
							</xsl:with-param>
						</xsl:apply-templates>
						<xsl:apply-templates select="." mode="param-group">
							<xsl:with-param name="style">
								header
							</xsl:with-param>
						</xsl:apply-templates>
					</div>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="wadl:*" mode="param-group">
		<xsl:param name="style" />
		<xsl:param name="prefix"></xsl:param>
		<xsl:if test="ancestor-or-self::wadl:*/wadl:param[@style=$style]">
			<table>
				<tr>
					<th>parameter</th>
					<th>value</th>
					<th>description</th>
				</tr>
				<xsl:apply-templates select="ancestor-or-self::wadl:*/wadl:param[@style=$style]" />
			</table>
		</xsl:if>
	</xsl:template>
	<xsl:template match="wadl:param">
		<tr>
			<td>
				<xsl:value-of select="@name" />
			</td>
			<td>
				<xsl:call-template name="link-qname">
					<xsl:with-param name="qname" select="@type" />
				</xsl:call-template>
			</td>
			<xsl:apply-templates select="wadl:doc" mode="table" />
		</tr>
	</xsl:template>
	<xsl:template match="wadl:link">
		<li>
			Link:
			<a href="#{@resource_type}">
				<xsl:value-of select="@rel" />
			</a>
		</li>
	</xsl:template>
	<xsl:template match="wadl:option">
		<li>
			<tt>
				<xsl:value-of select="@value" />
			</tt>
			<xsl:if test="ancestor::wadl:param[1]/@default=@value">
				<small> (default)</small>
			</xsl:if>
		</li>
	</xsl:template>
	<xsl:template match="wadl:option" mode="option-doc">
		<dt>
			<tt>
				<xsl:value-of select="@value" />
			</tt>
			<xsl:if test="ancestor::wadl:param[1]/@default=@value">
				<small> (default)</small>
			</xsl:if>
		</dt>
		<dd>
			<xsl:apply-templates select="wadl:doc" />
		</dd>
	</xsl:template>
	<xsl:template match="wadl:doc">
		<xsl:param name="inline">
			0
		</xsl:param>
		<!-- skip WADL elements -->
		<xsl:choose>
			<xsl:when test="node()[1]=text() and $inline=0">
				<p>
					<xsl:apply-templates select="node()" mode="copy" />
				</p>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="node()" mode="copy" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="wadl:doc" mode="table">
		<!-- skip WADL elements -->
		<xsl:apply-templates select="node()" mode="copy" />
	</xsl:template>
	<!-- utilities -->
	<xsl:template name="get-id">
		<xsl:choose>
			<xsl:when test="@id">
				<xsl:value-of select="@id" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="generate-id()" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="get-namespace-uri">
		<xsl:param name="context" select="." />
		<xsl:param name="qname" />
		<xsl:variable name="prefix" select="substring-before($qname,':')" />
		<xsl:variable name="qname-ns-uri" select="$context/namespace::*[name()=$prefix]" />
		<!-- nasty hack to get around libxsl's refusal to copy all namespace nodes when pushing nodesets around -->
		<xsl:choose>
			<xsl:when test="$qname-ns-uri">
				<xsl:value-of select="$qname-ns-uri" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="exsl:node-set($resources)/*[1]/attribute::*[namespace-uri()='urn:namespace' and local-name()=$prefix]" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="get-element">
		<xsl:param name="context" select="." />
		<xsl:param name="qname" />
		<xsl:variable name="ns-uri">
			<xsl:call-template name="get-namespace-uri">
				<xsl:with-param name="context" select="$context" />
				<xsl:with-param name="qname" select="$qname" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="localname" select="substring-after($qname, ':')" />
		<xsl:variable name="definition" select="exsl:node-set($grammars)/descendant::xs:element[@name=$localname][ancestor-or-self::*[@targetNamespace=$ns-uri]]" />
		<xsl:variable name='source' select="$definition/ancestor-or-self::wadl:include[1]/@href" />
		<p>
			<em>
				Source:
				<a href="{$source}">
					<xsl:value-of select="$source" />
				</a>
			</em>
		</p>
		<pre>
			<xsl:apply-templates select="$definition" mode="encode" />
		</pre>
	</xsl:template>
	<xsl:template name="link-qname">
		<xsl:param name="context" select="." />
		<xsl:param name="qname" />
		<xsl:variable name="ns-uri">
			<xsl:call-template name="get-namespace-uri">
				<xsl:with-param name="context" select="$context" />
				<xsl:with-param name="qname" select="$qname" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="localname" select="substring-after($qname, ':')" />
		<xsl:choose>
			<xsl:when test="$ns-uri='http://www.w3.org/2001/XMLSchema'">
				<a href="http://www.w3.org/TR/xmlschema-2/#{$localname}">
					<xsl:value-of select="$localname" />
				</a>
			</xsl:when>
			<xsl:when test="//xs:simpleType[@name=$qname]">
				<xsl:apply-templates select="//xs:simpleType[@name=$qname]" mode="custom-type-link" />
			</xsl:when>
			<xsl:when test="//xs:complexType[@name=$qname]">
				<xsl:apply-templates select="//xs:complexType[@name=$qname]" mode="custom-type-link" />
			</xsl:when>
			<xsl:otherwise>
				<a href="http://www.w3.org/TR/xmlschema-2/#string">string</a>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="//xs:complexType" mode="custom-type-link">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<a href="#{$name}">
			<xsl:value-of select="@name" />
		</a>
	</xsl:template>
	<xsl:template match="//xs:simpleType" mode="custom-type-link">
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<a href="#{$name}">
			<xsl:value-of select="@name" />
		</a>
	</xsl:template>
	<xsl:template name="expand-qname">
		<xsl:param name="context" select="." />
		<xsl:param name="qname" />
		<xsl:variable name="ns-uri">
			<xsl:call-template name="get-namespace-uri">
				<xsl:with-param name="context" select="$context" />
				<xsl:with-param name="qname" select="$qname" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:text>{</xsl:text>
		<xsl:value-of select="$ns-uri" />
		<xsl:text>} </xsl:text>
		<xsl:value-of select="substring-after($qname, ':')" />
	</xsl:template>
	<xsl:template name="representation-name">
		<xsl:variable name="expanded-name">
			<xsl:call-template name="expand-qname">
				<xsl:with-param select="@element" name="qname" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="wadl:doc[@title]">
				<xsl:value-of select="wadl:doc[@title][1]/@title" />
				<xsl:if test="@status or @mediaType or @element">
					(
				</xsl:if>
				<xsl:if test="@status">
					Status Code
				</xsl:if>
				<xsl:value-of select="@status" />
				<xsl:if test="@status and @mediaType">
					-
				</xsl:if>
				<xsl:value-of select="@mediaType" />
				<xsl:if test="(@status or @mediaType) and @element">
					-
				</xsl:if>
				<xsl:if test="@element">
					<abbr title="{$expanded-name}">
						<xsl:value-of select="@element" />
					</abbr>
				</xsl:if>
				<xsl:if test="@status or @mediaType or @element">
					)
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="@status">
					Status Code
				</xsl:if>
				<xsl:value-of select="@status" />
				<xsl:if test="@status and @mediaType">
					-
				</xsl:if>
				<xsl:value-of select="@mediaType" />
				<xsl:if test="@element">
					(
				</xsl:if>
				<abbr title="{$expanded-name}">
					<xsl:value-of select="@element" />
				</abbr>
				<xsl:if test="@element">
					)
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- entity-encode markup for display -->
	<xsl:template match="*" mode="encode">
		<xsl:text>&lt;</xsl:text>
		<xsl:value-of select="name()" />
		<xsl:apply-templates select="attribute::*" mode="encode" />
		<xsl:choose>
			<xsl:when test="*|text()">
				<xsl:text>&gt;</xsl:text>
				<xsl:apply-templates select="*|text()" mode="encode" xml:space="preserve" />
				<xsl:text>&lt;/</xsl:text>
				<xsl:value-of select="name()" />
				<xsl:text>&gt;</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>/&gt;</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="@*" mode="encode">
		<xsl:text> </xsl:text>
		<xsl:value-of select="name()" />
		<xsl:text>="</xsl:text>
		<xsl:value-of select="." />
		<xsl:text>"</xsl:text>
	</xsl:template>
	<xsl:template match="text()" mode="encode">
		<xsl:value-of select="." xml:space="preserve" />
	</xsl:template>
	<!-- copy HTML for display -->
	<xsl:template match="html:*" mode="copy">
		<!-- remove the prefix on HTML elements -->
		<xsl:element name="{local-name()}">
			<xsl:for-each select="@*">
				<xsl:attribute name="{local-name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>
			<xsl:apply-templates select="node()" mode="copy" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="@*|node()[namespace-uri()!='http://www.w3.org/1999/xhtml']" mode="copy">
		<!-- everything else goes straight through -->
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" mode="copy" />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>