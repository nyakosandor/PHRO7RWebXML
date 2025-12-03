<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes" />

  <xsl:template match="/">
    <html>
      <head>
        <title>Autók drágábbak mint 30,000</title>
      </head>
      <body>
        <h1>Autók drágábbak mint 30,000</h1>
        <p>
          <xsl:value-of select="count(autok/auto[ar &gt; 30000])" /> autó drágább mint 30,000.
        </p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>