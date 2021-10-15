package world.share.lib_internet.bean;

import java.util.List;

public class BookBean {


    private Boolean rewardable;
    private SettingBean setting;
    private Integer total_rewards_count;
    private List<SettingBean> reward_buyers;

    public Boolean getRewardable() {
        return rewardable;
    }

    public void setRewardable(Boolean rewardable) {
        this.rewardable = rewardable;
    }

    public SettingBean getSetting() {
        return setting;
    }

    public void setSetting(SettingBean setting) {
        this.setting = setting;
    }

    public Integer getTotal_rewards_count() {
        return total_rewards_count;
    }

    public void setTotal_rewards_count(Integer total_rewards_count) {
        this.total_rewards_count = total_rewards_count;
    }

    public List<SettingBean> getReward_buyers() {
        return reward_buyers;
    }

    public void setReward_buyers(List<SettingBean> reward_buyers) {
        this.reward_buyers = reward_buyers;
    }

    public static class SettingBean {
        private String description;
        private Integer default_amount;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getDefault_amount() {
            return default_amount;
        }

        public void setDefault_amount(Integer default_amount) {
            this.default_amount = default_amount;
        }
    }
}
