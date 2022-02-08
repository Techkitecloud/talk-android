/*
 *
 *   Nextcloud Talk application
 *
 *   @author Tim Krüger
 *   Copyright (C) 2021 Tim Krüger <t@timkrueger.me>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nextcloud.talk.models.json.status.predefined;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.nextcloud.talk.models.json.autocomplete.AutocompleteUser;
import com.nextcloud.talk.models.json.generic.GenericOCS;
import com.nextcloud.talk.models.json.status.Status;

import java.util.List;
import java.util.Objects;

import kotlin.jvm.JvmSuppressWildcards;

@JsonObject
public class PredefinedStatusOCS extends GenericOCS {
    @JsonField(name = "data")
    List<PredefinedStatus> data;

    public List<PredefinedStatus> getData() {
        return this.data;
    }

    public void setData(List<PredefinedStatus> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PredefinedStatusOCS that = (PredefinedStatusOCS) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }

    @Override
    public String toString() {
        return "PredefinedStatusOCS{" +
            "data=" + data +
            '}';
    }

}
