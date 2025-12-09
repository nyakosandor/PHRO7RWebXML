<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes" />

  <xsl:template match="/">
    <html>
      <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Hallgatok adatai - for-each, value-of</title>
        <style>
          table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
          }
          th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
          }
          th {
            background-color: #4CAF50;
            color: white;
          }
          tr:nth-child(even) {
            background-color: #f2f2f2;
          }
          h1 {
            text-align: center;
          }
        </style>
      </head>
      <body>
        <h1>Hallgatok adatai - for-each, value-of</h1>
        <table>
          <tr>
            <th>ID</th>
            <th>Vezet&#233;kn&#233;v</th>
            <th>Keresztn&#233;v</th>
            <th>Becen&#233;v</th>
            <th>Kor</th>
            <th>&#214;szt&#246;nd&#237;j</th>
          </tr>
          <xsl:for-each select="class/student">
            <tr>
              <td><xsl:value-of select="@id" /></td>
              <td><xsl:value-of select="vezeteknev" /></td>
              <td><xsl:value-of select="keresztnev" /></td>
              <td><xsl:value-of select="becenev" /></td>
              <td><xsl:value-of select="kor" /></td>
              <td><xsl:value-of select="osztondij" /></td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
