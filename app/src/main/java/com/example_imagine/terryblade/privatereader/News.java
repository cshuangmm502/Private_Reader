package com.example_imagine.terryblade.privatereader;

import java.util.List;

/**
 * Created by Terryblade on 2017/4/29.
 */
public class News {
    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2015-09-23 00:00","title":"\u201c2015北京影视剧非洲展播季\u201d隆重启动","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421877944_ss.jpg","url":"http://news.sohu.com/20150923/n421880177.shtml"},{"ctime":"2015-09-23 07:20","title":"道德模范刘婷变性后参赛选美 婉拒多名追求者","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421877438_ss.jpg","url":"http://news.sohu.com/20150923/n421877943.shtml"},{"ctime":"2015-09-23 07:20","title":"车熄火溜坡两男孩坠入水库 死者家属索赔204万","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421868365_ss.jpg","url":"http://news.sohu.com/20150923/n421877913.shtml"},{"ctime":"2015-09-23 07:15","title":"小伙把银行卡卖给别人洗黑钱 卷入特大诈骗案被抓","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421868656_ss.jpg","url":"http://news.sohu.com/20150923/n421877708.shtml"},{"ctime":"2015-09-23 07:11","title":"南昌真马街头踢坏宝马 马主人：马路是马走的路","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421871509_ss.jpg","url":"http://news.sohu.com/20150923/n421877436.shtml"},{"ctime":"2015-09-23 02:53","title":"14岁女孩为向父母证明可养活自己殴打失足女要钱","description":"搜狐社会","picUrl":"","url":"http://news.sohu.com/20150923/n421867521.shtml"},{"ctime":"2015-09-23 03:10","title":"盗贼留字条称后视镜在我这 民警留字条：他已抓","description":"搜狐社会","picUrl":"","url":"http://news.sohu.com/20150923/n421868362.shtml"},{"ctime":"2015-09-23 13:59","title":"北京职务犯罪98%是大案 保外就医两厅官被收监","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421919685_ss.jpg","url":"http://news.sohu.com/20150923/n421920528.shtml"},{"ctime":"2015-09-23 13:56","title":"葛兰素史克被指致使儿童吃错药 涉事药中国有售","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421919511_ss.png","url":"http://news.sohu.com/20150923/n421920338.shtml"},{"ctime":"2015-09-23 13:54","title":"演员方子哥为儿买婚房被骗近400万 获赔30万","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20150923/Img421919412_ss.jpg","url":"http://news.sohu.com/20150923/n421920205.shtml"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2015-09-23 00:00
         * title : “2015北京影视剧非洲展播季”隆重启动
         * description : 搜狐社会
         * picUrl : http://photocdn.sohu.com/20150923/Img421877944_ss.jpg
         * url : http://news.sohu.com/20150923/n421880177.shtml
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;
        private int imageId;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getImageId(){return imageId;}

        public void setImageId(int imageId){this.imageId=imageId;}
    }
}