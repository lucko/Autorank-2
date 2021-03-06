package me.armar.plugins.autorank.playerchecker.requirement;

import org.bukkit.entity.Player;

import me.armar.plugins.autorank.language.Lang;
import me.staartvin.statz.hooks.Dependency;
import me.staartvin.statz.hooks.handlers.RPGmeHandler;

public class RPGMeSkillLevelRequirement extends Requirement {

	// [0] skillName, [1] skillLevel
	// private final List<String> skillsCombined = new ArrayList<String>();
	private int skillLevel = -1;
	private String skillName = "all";
	private RPGmeHandler handler = null;

	@Override
	public String getDescription() {

		if (skillName.equals("all") || skillName.equals("none")) {
			return Lang.RPGME_SKILL_LEVEL_REQUIREMENT.getConfigValue(skillLevel + "", "all skills");
		} else {
			return Lang.RPGME_SKILL_LEVEL_REQUIREMENT.getConfigValue(skillLevel + "", skillName);
		}
	}

	@Override
	public String getProgress(final Player player) {
		int level = 0;

		if (skillName.equalsIgnoreCase("all")) {
			level = handler.getTotalLevel(player);
		} else {
			level = handler.getSkillLevel(player, skillName);
		}

		return level + "/" + skillLevel;
	}

	@Override
	public boolean meetsRequirement(final Player player) {

		if (!handler.isAvailable())
			return false;

		if (skillName.equalsIgnoreCase("all")) {
			return handler.getTotalLevel(player) >= skillLevel;
		} else {
			return handler.getSkillLevel(player, skillName) >= skillLevel;
		}
	}

	@Override
	public boolean setOptions(final String[] options) {

		handler = (RPGmeHandler) this.getDependencyManager().getDependencyHandler(Dependency.RPGME);

		if (options.length > 0) {
			skillLevel = Integer.parseInt(options[0]);
		}
		if (options.length > 1) {
			skillName = options[1];
		}
		return skillLevel != -1 && handler != null && handler.isAvailable();
	}
}
