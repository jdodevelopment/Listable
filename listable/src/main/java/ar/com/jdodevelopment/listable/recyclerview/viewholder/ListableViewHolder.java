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

import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.com.jdodevelopment.listable.annotation.ListableField;
import ar.com.jdodevelopment.listable.formatter.ListableFormatter;
import ar.com.jdodevelopment.listable.recyclerview.adapter.ListableAdapter;

/**
 *
 * @author Juan Daniel Ornella
 */
public class ListableViewHolder<T> extends RecyclerView.ViewHolder {


    /**
     * Represent the entity than you want to list
     */
    private Class entityClass;

    /**
     * Map of fields and views defined in the class with the Annotation '@ListableField'
     */
    private Map<Field, View> listableViews;


    /**
     * @param entityClass the class that wrap the entity that will be listed.
     * @param itemView the view inflated to display the data
     */
    public ListableViewHolder(View itemView, Class entityClass) {
        super(itemView);
        this.entityClass = entityClass;
        listableViews = new ArrayMap<>();
        initListableViews();
    }


    /**
     * Init the views corresponding to each field
     *
     * @throws IllegalStateException when entityClass haven't declared in any the Annotation '@ListableField'.
     */
    private void initListableViews() {
        List<Field> listableFields = getListableFields();
        if (listableFields.isEmpty())
            throw new IllegalStateException("You must declare at least one @ListableField in the class:" + entityClass.getSimpleName());

        for (Field field : listableFields) {
            View view = getListableFieldView(field);
            listableViews.put(field, view);
        }
    }

    /**
     * @return the list of fields that have the Annotation '@ListableField'.
     */
    private List<Field> getListableFields() {
        List<Field> list = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            ListableField listableField = field.getAnnotation(ListableField.class);
            if (listableField != null) {
                list.add(field);
            }
        }
        return list;
    }

    /**
     * Find the corresponding view of a field.
     * @param field target field.
     * @return finded view in itemView.
     * @throws IllegalStateException if the view is not found in itemView
     */
    private View getListableFieldView(Field field) {
        ListableField listableField = field.getAnnotation(ListableField.class);
        int viewResource = listableField.viewResource();
        View view = itemView.findViewById(viewResource);
        if (view == null) {
            String message = "The view with id: " + viewResource + " could not be found, in the field: " + field.getName();
            throw new IllegalStateException(message);
        }
        return view;
    }


     /**
     * Set all values ​​in their respective views.
      *
     * @param object to set values in each view
     */
    public void setValuesInViews(final T object) {
        for (Map.Entry<Field, View> entry : listableViews.entrySet()) {
            View view = entry.getValue();
            Field field = entry.getKey();
            Object fieldValue = getFieldValueByReflection(field, object);
            setValueInView(view, field, fieldValue);
        }
    }

    /**
     * Set the value in the corresponding view.
     *
     * @param view target view to set value
     * @param field target field from which the formatter is obtained.
     * @param fieldValue the value to in in the view.
     */
    private void setValueInView(View view, Field field, Object fieldValue){
        ListableFormatter listableFormatter = createFotmatter(field);
        if(fieldValue != null){
            setValueIfSupported(listableFormatter, view, fieldValue);
        }else{
            listableFormatter.setNullInView(view);
        }
    }


    /**
     *
     * @throws IllegalStateException if the type of view is not supported by the {@link ListableFormatter}
     * @throws IllegalStateException if the type of fieldValue is not supported by the {@link ListableFormatter}
     */
    private void setValueIfSupported( ListableFormatter listableFormatter, View view, Object fieldValue) {
        if(!listableFormatter.supportedValueType(fieldValue)){
            throw new IllegalStateException(listableFormatter.getClass().getSimpleName() + " not support field value type: " + fieldValue.getClass());
        }
        if(!listableFormatter.supportedViewType(view)){
            throw new IllegalStateException(listableFormatter.getClass().getSimpleName() + " not support view type: " + view.getClass());
        }
        listableFormatter.setValueInView(view, fieldValue);
    }

    /**
     * @return the value of a field obtained by reflection.
     */
    private Object getFieldValueByReflection(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Innstantiate a {@link ListableFormatter} defined in the Annotation '{@link ListableField}'.
     *
     * @param field that have declared the {@link ListableField}.
     * @return instantiated {@link ListableFormatter}.
     */
    private ListableFormatter createFotmatter(Field field) {
        try {
            ListableField listableField = field.getAnnotation(ListableField.class);
            Class formatterClass = listableField.formatter();
            return (ListableFormatter) formatterClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Register a callback to be invoked when the itemView is clicked.
     *
     * @param onListableClickListener The callback that will run
     */
    public void setOnListableClickListener(ListableAdapter.OnListableClickListener<T> onListableClickListener, T object) {
        itemView.setOnClickListener(view -> onListableClickListener.onListableClick(view, object));
    }

    /**
     * Register a callback to be invoked when the itemView is clicked and held.
     *
     * @param onListableLongClickListener The callback that will run
     */
    public void setOnListableLongClickListener(ListableAdapter.OnListableLongClickListener<T> onListableLongClickListener, T object) {
        itemView.setOnLongClickListener(view -> {
            onListableLongClickListener.onListableLongClick(view, object);
            return false;
        });
    }

}

