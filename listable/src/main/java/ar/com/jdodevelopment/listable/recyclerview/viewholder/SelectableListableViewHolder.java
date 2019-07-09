/*
    Copyright (c) 2019 Juan Daniel Ornella <juan.daniel.sp@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package ar.com.jdodevelopment.listable.recyclerview.viewholder;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import ar.com.jdodevelopment.listable.R;


/**
 *
 * @author Juan Daniel Ornella
 */
public class SelectableListableViewHolder<T> extends ListableViewHolder<T> {


    private final Drawable backgroundInitial;
    private Drawable backgroundSelected;
    private Drawable backgroundUnselected;


    public SelectableListableViewHolder(View view, Class entityClass) {
        super(view, entityClass);
        backgroundInitial = itemView.getBackground();
        backgroundSelected = ContextCompat.getDrawable(view.getContext(), R.color.backgroundSelected);
        backgroundUnselected = ContextCompat.getDrawable(view.getContext(), R.color.backgroundUnselected);
    }


    public void updateSelection(boolean selectionEnabled, boolean selected) {
        if (selectionEnabled) {
            if (selected) {
                itemView.setBackground(backgroundSelected);
            } else {
                itemView.setBackground(backgroundUnselected);
            }
        } else {
            itemView.setBackground(backgroundInitial);
        }
    }


    public void setBackgroundSelected(Drawable backgroundSelected) {
        this.backgroundSelected = backgroundSelected;
    }


    public void setBackgroundUnselected(Drawable backgroundUnselected) {
        this.backgroundUnselected = backgroundUnselected;
    }


}
