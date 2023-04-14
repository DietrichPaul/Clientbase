/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.config.ext.json;

import com.google.gson.JsonArray;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.config.ext.JsonConfig;

public abstract class JsonArrayConfig extends JsonConfig<JsonArray> {

    public JsonArrayConfig(String name, ConfigType type) {
        super(name, type);
    }

    @Override
    protected JsonArray make() {
        return new JsonArray();
    }
}
