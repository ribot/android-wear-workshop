package uk.co.ribot.androidwear.model;

import com.google.gson.annotations.Expose;

public class Board {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String desc;
    @Expose
    private Object descData;
    @Expose
    private Boolean closed;
    @Expose
    private Object idOrganization;
    @Expose
    private Boolean pinned;
    @Expose
    private String url;
    @Expose
    private String shortUrl;
    @Expose
    private Prefs prefs;
    @Expose
    private LabelNames labelNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getDescData() {
        return descData;
    }

    public void setDescData(Object descData) {
        this.descData = descData;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Object getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(Object idOrganization) {
        this.idOrganization = idOrganization;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }

    public LabelNames getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(LabelNames labelNames) {
        this.labelNames = labelNames;
    }

    public class LabelNames {

        @Expose
        private String red;
        @Expose
        private String orange;
        @Expose
        private String yellow;
        @Expose
        private String green;
        @Expose
        private String blue;
        @Expose
        private String purple;

        public String getRed() {
            return red;
        }

        public void setRed(String red) {
            this.red = red;
        }

        public String getOrange() {
            return orange;
        }

        public void setOrange(String orange) {
            this.orange = orange;
        }

        public String getYellow() {
            return yellow;
        }

        public void setYellow(String yellow) {
            this.yellow = yellow;
        }

        public String getGreen() {
            return green;
        }

        public void setGreen(String green) {
            this.green = green;
        }

        public String getBlue() {
            return blue;
        }

        public void setBlue(String blue) {
            this.blue = blue;
        }

        public String getPurple() {
            return purple;
        }

        public void setPurple(String purple) {
            this.purple = purple;
        }
    }

    public class Prefs {

        @Expose
        private String permissionLevel;
        @Expose
        private String voting;
        @Expose
        private String comments;
        @Expose
        private String invitations;
        @Expose
        private Boolean selfJoin;
        @Expose
        private Boolean cardCovers;
        @Expose
        private String cardAging;
        @Expose
        private String background;
        @Expose
        private String backgroundColor;
        @Expose
        private Object backgroundImage;
        @Expose
        private Object backgroundImageScaled;
        @Expose
        private Boolean backgroundTile;
        @Expose
        private String backgroundBrightness;
        @Expose
        private Boolean canBePublic;
        @Expose
        private Boolean canBeOrg;
        @Expose
        private Boolean canBePrivate;
        @Expose
        private Boolean canInvite;

        public String getPermissionLevel() {
            return permissionLevel;
        }

        public void setPermissionLevel(String permissionLevel) {
            this.permissionLevel = permissionLevel;
        }

        public String getVoting() {
            return voting;
        }

        public void setVoting(String voting) {
            this.voting = voting;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getInvitations() {
            return invitations;
        }

        public void setInvitations(String invitations) {
            this.invitations = invitations;
        }

        public Boolean getSelfJoin() {
            return selfJoin;
        }

        public void setSelfJoin(Boolean selfJoin) {
            this.selfJoin = selfJoin;
        }

        public Boolean getCardCovers() {
            return cardCovers;
        }

        public void setCardCovers(Boolean cardCovers) {
            this.cardCovers = cardCovers;
        }

        public String getCardAging() {
            return cardAging;
        }

        public void setCardAging(String cardAging) {
            this.cardAging = cardAging;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public Object getBackgroundImage() {
            return backgroundImage;
        }

        public void setBackgroundImage(Object backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        public Object getBackgroundImageScaled() {
            return backgroundImageScaled;
        }

        public void setBackgroundImageScaled(Object backgroundImageScaled) {
            this.backgroundImageScaled = backgroundImageScaled;
        }

        public Boolean getBackgroundTile() {
            return backgroundTile;
        }

        public void setBackgroundTile(Boolean backgroundTile) {
            this.backgroundTile = backgroundTile;
        }

        public String getBackgroundBrightness() {
            return backgroundBrightness;
        }

        public void setBackgroundBrightness(String backgroundBrightness) {
            this.backgroundBrightness = backgroundBrightness;
        }

        public Boolean getCanBePublic() {
            return canBePublic;
        }

        public void setCanBePublic(Boolean canBePublic) {
            this.canBePublic = canBePublic;
        }

        public Boolean getCanBeOrg() {
            return canBeOrg;
        }

        public void setCanBeOrg(Boolean canBeOrg) {
            this.canBeOrg = canBeOrg;
        }

        public Boolean getCanBePrivate() {
            return canBePrivate;
        }

        public void setCanBePrivate(Boolean canBePrivate) {
            this.canBePrivate = canBePrivate;
        }

        public Boolean getCanInvite() {
            return canInvite;
        }

        public void setCanInvite(Boolean canInvite) {
            this.canInvite = canInvite;
        }
    }
}