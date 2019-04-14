---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by mike.
--- DateTime: 14.04.19 0:07
---

local RPD = require "scripts/lib/commonClasses"

local buff = require "scripts/lib/buff"

local RegenerationDelay = 10

return buff.init{
    desc  = function ()
        return {
            icon          = 3,
            name          = "Regeneration_Name",
            info          = "Regeneration_Info",
        }
    end,

    act = function(self,buff)

        local ch = buff.target

        if not ch:isAlive() then
            return
        end

        if not ch:isStarving() then
            ch:heal(1, buff)
        end

        local bonus = ch:buffLevel("Rejuvenation")

        bonus = bonus + ch:buffLevel("DieHard")

        local delay = RegenerationDelay/(1.2^bonus)
        buff:spend(delay)
    end,

    attachTo = function(self, buff, target)
        if target:buffLevel("Regeneration") > 1 then
            return false
        end

        return true
    end
}
