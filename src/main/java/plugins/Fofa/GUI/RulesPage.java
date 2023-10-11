package plugins.Fofa.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class RulesPage {
    VBox vBoxRoot;

    VBox vBoxShow;

    VBox vBoxTmp;

    Label labelShow;

    Label labelTmp;

    TextArea textAreaShow;

    TextArea textAreaTmp;

    public Node show(){
        labelShow = new Label("语法规则：");
        textAreaShow = new TextArea();
        textAreaShow.appendText("逻辑连接符\t【具体含义】\n" +
                "=\t【匹配，=\"\"时，可查询不存在字段或者值为空的情况。】\n" +
                "==\t【完全匹配，==\"\"时，可查询存在且值为空的情况。】\n" +
                "&&\t【与】\n" +
                "||\t【或者】\n" +
                "!=\t【不匹配，!=\"\"时，可查询值为空的情况。】\n" +
                "*=\t【模糊匹配，使用*或者?进行搜索，比如banner*=\"mys??\" (个人版及以上可用)。】\n" +
                "()\t【确认查询优先级，括号内容优先级最高。】\n" +
                "\n例句(点击可去搜索)\t【用途说明】\t注\n" +
                "title=\"beijing\"\t【从标题中搜索“北京”】\t-\n" +
                "header=\"elastic\"\t【从http头中搜索“elastic”】\t-\n" +
                "body=\"网络空间测绘\"\t【从html正文中搜索“网络空间测绘”】\t-\n" +
                "fid=\"sSXXGNUO2FefBTcCLIT/2Q==\"\t【查找相同的网站指纹】\t搜索网站类型资产\n" +
                "domain=\"qq.com\"\t【搜索根域名带有qq.com的网站】\t-\n" +
                "icp=\"京ICP证030173号\"\t【查找备案号为“京ICP证030173号”的网站】\t搜索网站类型资产\n" +
                "js_name=\"js/jquery.js\"\t【查找网站正文中包含js/jquery.js的资产】\t搜索网站类型资产\n" +
                "js_md5=\"82ac3f14327a8b7ba49baa208d4eaa15\"\t【查找js源码与之匹配的资产】\t-\n" +
                "cname=\"ap21.inst.siteforce.com\"\t【查找cname为\"ap21.inst.siteforce.com\"的网站】\t-\n" +
                "cname_domain=\"siteforce.com\"\t【查找cname包含“siteforce.com”的网站】\t-\n" +
                "cloud_name=\"Aliyundun\"\t【new 通过云服务名称搜索资产】\t-\n" +
                "product=\"NGINX\"\t【new 搜索此产品的资产】\t个人版及以上可用\n" +
                "category=\"服务\"\t【new 搜索此产品分类的资产】\t个人版及以上可用\n" +
                "sdk_hash==\"Mkb4Ms4R96glv/T6TRzwPWh3UDatBqeF\"\t【new 搜索使用此sdk的资产】\t商业版及以上可用\n" +
                "icon_hash=\"-247388890\"\t【搜索使用此 icon 的资产】\t-\n" +
                "host=\".gov.cn\"\t【从url中搜索“.gov.cn”】\t搜索要用host作为名称\n" +
                "port=\"6379\"\t【查找对应“6379”端口的资产】\t-\n" +
                "ip=\"1.1.1.1\"\t【从ip中搜索包含“1.1.1.1”的网站】\t搜索要用ip作为名称\n" +
                "ip=\"220.181.111.1/24\"\t【查询IP为“220.181.111.1”的C网段资产】\t-\n" +
                "status_code=\"402\"\t【查询服务器状态为“402”的资产】\t查询网站类型数据\n" +
                "protocol=\"quic\"\t【查询quic协议资产】\t搜索指定协议类型(在开启端口扫描的情况下有效)\n" +
                "country=\"CN\"\t【搜索指定国家(编码)的资产】\t-\n" +
                "region=\"Xinjiang Uyghur Autonomous Region\"\t【搜索指定行政区的资产】\t-\n" +
                "city=\"Ürümqi\"\t【搜索指定城市的资产】\t-\n" +
                "cert=\"baidu\"\t【搜索证书(https或者imaps等)中带有baidu的资产】\t-\n" +
                "cert.subject=\"Oracle Corporation\"\t【搜索证书持有者是Oracle Corporation的资产】\t-\n" +
                "cert.issuer=\"DigiCert\"\t【搜索证书颁发者为DigiCert Inc的资产】\t-\n" +
                "cert.is_valid=true\t【验证证书是否有效，true有效，false无效】\t个人版及以上可用\n" +
                "cert.is_match=true\t【new 证书和域名是否匹配；true匹配、false不匹配】\t个人版及以上可用\n" +
                "cert.is_expired=false\t【new 证书是否过期；true过期、false未过期】\t个人版及以上可用\n" +
                "cert.subject.org=\"Oracle Corporation\"\t【new 搜索证书持有者的组织】\t-\n" +
                "cert.subject.cn=\"baidu.com\"\t【new 搜索证书持有者的通用名称】\t-\n" +
                "cert.issuer.org=\"cPanel, Inc.\"\t【new 搜索证书颁发者的组织】\t-\n" +
                "cert.issuer.cn=\"Synology Inc. CA\"\t【new 搜索证书颁发者的通用名称】\t-\n" +
                "jarm=\"2ad...83e81\"\t【搜索JARM指纹】\t-\n" +
                "banner=\"users\" && protocol=\"ftp\"\t【搜索FTP协议中带有users文本的资产】\t-\n" +
                "type=\"service\"\t【搜索所有协议资产，支持subdomain和service两种】\t搜索所有协议资产\n" +
                "os=\"centos\"\t【搜索CentOS资产】\t-\n" +
                "server==\"Microsoft-IIS/10\"\t【搜索IIS 10服务器】\t-\n" +
                "app=\"Microsoft-Exchange\"\t【搜索Microsoft-Exchange设备】\t-\n" +
                "after=\"2017\" && before=\"2017-10-01\"\t【时间范围段搜索】\t-\n" +
                "asn=\"19551\"\t【搜索指定asn的资产】\t-\n" +
                "org=\"LLC Baxet\"\t【搜索指定org(组织)的资产】\t-\n" +
                "base_protocol=\"udp\"\t【搜索指定udp协议的资产】\t-\n" +
                "is_fraud=false\t【new 排除仿冒/欺诈数据】\t专业版及以上可用\n" +
                "is_honeypot=false\t【排除蜜罐数据】\t专业版及以上可用\n" +
                "is_ipv6=true\t【搜索ipv6的资产】\t搜索ipv6的资产,只接受true和false\n" +
                "is_domain=true\t【搜索域名的资产】\t搜索域名的资产,只接受true和false\n" +
                "is_cloud=true\t【new 筛选使用了云服务的资产】\t-\n" +
                "port_size=\"6\"\t【查询开放端口数量等于\"6\"的资产】\t个人版及以上可用\n" +
                "port_size_gt=\"6\"\t【查询开放端口数量大于\"6\"的资产】\t个人版及以上可用\n" +
                "port_size_lt=\"12\"\t【查询开放端口数量小于\"12\"的资产】\t个人版及以上可用\n" +
                "ip_ports=\"80,161\"\t【搜索同时开放80和161端口的ip】\t搜索同时开放80和161端口的ip资产(以ip为单位的资产数据)\n" +
                "ip_country=\"CN\"\t【搜索中国的ip资产(以ip为单位的资产数据)】\t搜索中国的ip资产\n" +
                "ip_region=\"Zhejiang\"\t【搜索指定行政区的ip资产(以ip为单位的资产数据)】\t搜索指定行政区的资产\n" +
                "ip_city=\"Hangzhou\"\t【搜索指定城市的ip资产(以ip为单位的资产数据)】\t搜索指定城市的资产\n" +
                "ip_after=\"2021-03-18\"\t【搜索2021-03-18以后的ip资产(以ip为单位的资产数据)】\t搜索2021-03-18以后的ip资产\n" +
                "ip_before=\"2019-09-09\"\t【搜索2019-09-09以前的ip资产(以ip为单位的资产数据)】\t搜索2019-09-09以前的ip资产\n" +
                "\n例如：\n" +
                "\"BIG-IP\" && country!=\"CN\" && status_code=\"200\"");
        textAreaShow.setEditable(false);
        textAreaShow.setWrapText(true);
        vBoxShow = new VBox(10);
        vBoxShow.getChildren().addAll(labelShow, textAreaShow);

        labelTmp = new Label("语法组装处：");
        textAreaTmp = new TextArea();
        vBoxTmp = new VBox(10);
        vBoxTmp.getChildren().addAll(labelTmp, textAreaTmp);

        vBoxRoot = new VBox(10);
        vBoxRoot.setPadding(new Insets(10));
        vBoxRoot.setAlignment(Pos.CENTER);
        vBoxRoot.getChildren().addAll(vBoxShow, vBoxTmp);
        return vBoxRoot;
    }
}
