class Plataforma2D {
    constructor(characterInfo, impCharacterInfo,
                characterProgress, impCharacterProgress, rangeTypeCharacter,
                collectable, impCollectable,
                habilities, impHabilities, nHabilities, habilitieUseType,
                health, impHealth, rangeTypeHealth,
                levelProgress, impLevelProgress, rangeTypeLevelProgress, levelProgressType,
                score, impScore,
                shields, impShields,
                time, impTime, timeUse,
                weapons, impWeapons, nWeapons, weaponUseType) {

      this.characterInfo = {
        characterInfo, impCharacterInfo
      }
      this.characterProgress = {
        characterProgress, impCharacterProgress, rangeTypeCharacter
      }
      this.collectable = {
        collectable, impCollectable
      }
      this.habilities = {
        habilities, impHabilities, nHabilities, habilitieUseType
      }
      this.health = {
        health, impHealth, rangeTypeHealth
      }
      this.levelProgress = {
        levelProgress, impLevelProgress, rangeTypeLevelProgress, levelProgressType
      }
      this.score = {
        score, impScore
      }
      this.shields = {
          shields, impShields
      }
      this.time = {
          time, impTime, timeUse
      }
      this.weapons = {
        weapons, impWeapons, nWeapons, weaponUseType
      }
    }
}