package plugins.Fofa.GUI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import plugins.Fofa.Config.GlobalConfig;

public class RootPageAdvanced {
    public Node show(){



        VBox vBoxFunc = new VBox();
        vBoxFunc.getChildren().addAll(
//                new Items().itemSpinner("size","获取的数量：",  0, 10000, 100, 1)
                new Items().itemTextField("size","获取的数量(max:10000)：",
                        GlobalConfig.CONFIG.getAdvanceds().get("size").getValue()),
                new Items().itemTextField("next", "查询页数：",
                        GlobalConfig.CONFIG.getAdvanceds().get("next").getValue()),
                new Items().itemTitledPane("fields", "可选字段", advancedContent())
        );
        return vBoxFunc;
    }

    Node advancedContent(){

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(5,10,5,10));
        vBox.getChildren().addAll(
                new Items().itemSub("link", "资产的URL链接"),
                new Items().itemSub("host", "主机名/URL"),
                new Items().itemSub("ip", "IP地址"),
                new Items().itemSub("port", "端口"),
                new Items().itemSub("domain", "域名"),
                new Items().itemSub("protocol", "协议名"),
                new Items().itemSub("os", "操作系统"),
                new Items().itemSub("icp", "icp备案号"),
                new Items().itemSub("title", "网站标题"),
                new Items().itemSub("header", "网站header"),
                new Items().itemSub("server", "网站server"),
                new Items().itemSub("country", "国家代码"),
                new Items().itemSub("country_name", "国家名"),
                new Items().itemSub("region", "区域"),
                new Items().itemSub("city", "城市"),
                new Items().itemSub("longitude", "地理位置 经度"),
                new Items().itemSub("latitude", "地理位置 纬度"),
                new Items().itemSub("as_number", "asn编号"),
                new Items().itemSub("as_organization", "asn组织"),
                new Items().itemSub("jarm", "jarm 指纹"),
                new Items().itemSub("banner", "协议 banner"),
                new Items().itemSub("cert", "证书"),
                new Items().itemSub("base_protocol", "基础协议，比如tcp/udp"),
                new Items().itemSub("certs_issuer_org", "证书颁发者组织"),
                new Items().itemSub("certs_issuer_cn", "证书颁发者通用名称"),
                new Items().itemSub("certs_subject_org", "证书持有者组织"),
                new Items().itemSub("certs_subject_cn", "证书持有者通用名称"),
                new Items().itemSub("product", "产品名【专】"),
                new Items().itemSub("product_category", "产品分类【专】"),
                new Items().itemSub("version", "版本号【专】"),
                new Items().itemSub("lastupdatetime", "FOFA最后更新时间【专】"),
                new Items().itemSub("cname", "域名cname【专】"),
                new Items().itemSub("icon_hash", "返回的icon_hash值【商】"),
                new Items().itemSub("certs_valid", "证书是否有效【商】"),
                new Items().itemSub("cname_domain", "cname的域名【商】"),
                new Items().itemSub("body", "网站正文内容【商】"),
                new Items().itemSub("icon", "icon 图标【企】"),
                new Items().itemSub("fid", "fid【企】"),
                new Items().itemSub("structinfo", "结构化信息 (部分协议支持、\n比如elastic、mongodb)【企】")
        );
        return vBox;
    }
}
