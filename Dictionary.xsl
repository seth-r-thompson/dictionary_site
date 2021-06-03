<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<div class="wordlist">
			<h2>Words</h2>
			<table>
				<tr>
					<th>Word</th>
					<th>Pronunciation</th>
					<th>Class</th>
					<th>Definitions</th>
				</tr>
				<xsl:for-each select="dictionary/entry">
					<tr>
						<td>
							<xsl:value-of select="lemma" />
						</td>
						<td>
							<xsl:value-of select="pronunciation" />
						</td>
						<td>
							<xsl:value-of select="wordclass" />
						</td>
						<td style="width: 75%">
							<xsl:value-of select="definitions" />
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</div>
	</xsl:template>
</xsl:stylesheet>