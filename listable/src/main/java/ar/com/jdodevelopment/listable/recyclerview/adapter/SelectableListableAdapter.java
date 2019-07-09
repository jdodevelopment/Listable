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

package ar.com.jdodevelopment.listable.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.jdodevelopment.listable.recyclerview.viewholder.ListableViewHolder;
import ar.com.jdodevelopment.listable.recyclerview.viewholder.SelectableListableViewHolder;


/**
 * Extension of {@link ListableAdapter} to make selectable the items of the list
 *
 * @author Juan Daniel Ornella
 * @param <T> the class that want to list
 */
public class SelectableListableAdapter<T> extends  ListableAdapter<T> {



    /**
     * Determine if the selection is enabled or disbled in the RecyclerView.
     */
    private boolean selectionEnabled;


    /**
     * Contains the list of indices of the objects that are currently selected.
     */
    private final Set<Integer> selectedIndexes;

    /**
     * @param entityClass the class that wrap the entity that will be listed.
     */
    public SelectableListableAdapter(Class entityClass) {
        super(entityClass);
        selectedIndexes = new HashSet<>();
    }

    /**
     * @param entityClass the class that wrap the entity that will be listed.
     * @param layoutId    override the value 'layoutResource' of the Annotation '@ListableEntity'
     *                    for the purpose of declaring multiple views of the same entity
     */
    public SelectableListableAdapter(Class entityClass, int layoutId) {
        this(entityClass);
        this.layoutId = layoutId;
    }


    /**
     * Clear the current selection
     */
    public void clearSelection(){
        selectedIndexes.clear();
    }


    /**
     * @return a List of current seleted objects
     */
    public List<T> getSelectedObjects(){
        List<T> selectedObjects = new ArrayList<>();
        for(Integer index : selectedIndexes){
            T selectedObject = list.get(index);
            selectedObjects.add(selectedObject);
        }
        return selectedObjects;
    }


    /**
     * @return a List of current seleted indexes
     */
    public List<Integer> getSelectedIndexes(){
        return new ArrayList<>(selectedIndexes);
    }


    /**
     * Allows to change the current selection status of an object
     *
     * @param object that change the state of selection
     */
    public void flipObjectSelection(T object){
        int index = list.indexOf(object);
        if(!selectedIndexes.contains(index)){
            selectedIndexes.add(index);
        }else{
            selectedIndexes.remove(index);
        }
    }


    @NonNull
    @Override
    public ListableViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(this.layoutId, parent, false);
        return new SelectableListableViewHolder<>(view, entityClass);
    }


    @Override
    public void onBindViewHolder(@NonNull ListableViewHolder<T> viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        boolean selected = selectedIndexes.contains(position);

        SelectableListableViewHolder selectableListableViewHolder = (SelectableListableViewHolder) viewHolder;
        selectableListableViewHolder.updateSelection(selectionEnabled, selected);
    }


    public boolean isSelectionEnabled() {
        return selectionEnabled;
    }

    public void setSelectionEnabled(boolean selectionEnabled) {
        this.selectionEnabled = selectionEnabled;
    }
}
