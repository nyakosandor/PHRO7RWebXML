<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes" />

  <xsl:template match="/">
    <html>
      <head>
        <title>Autók rendszáma és ára</title>
      </head>
      <body>
        <h1>Autók rendszáma és ára (ár szerint rendezve)</h1>
        <ul>
          <xsl:for-each select="autok/auto">
            <xsl:sort select="ar" data-type="number" order="ascending" />
            <li>
              Rendszám: <xsl:value-of select="@rsz" />, Ár: <xsl:value-of select="ar" />
            </li>
          </xsl:for-each>
        </ul>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>