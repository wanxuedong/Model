package world.share.lib_base.internet.bean

class BookBean {
    var rewardable: Boolean? = null
    var setting: SettingBean? = null
    var total_rewards_count: Int? = null
    var reward_buyers: List<SettingBean>? = null

    class SettingBean {
        var description: String? = null
        var default_amount: Int? = null
    }
}