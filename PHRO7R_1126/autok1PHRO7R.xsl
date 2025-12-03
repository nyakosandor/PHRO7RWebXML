<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes" />

  <xsl:template match="/">
    <html>
      <head>
        <title>Autók rendszámai</title>
      </head>
      <body>
        <h1>Autók rendszámai</h1>
        <ul>
          <xsl:for-each select="autok/auto">
            <li><xsl:value-of select="@rsz" /></li>
          </xsl:for-each>
        </ul>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>