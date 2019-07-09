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

package ar.com.jdodevelopment.listable.formatter;

import android.support.annotation.NonNull;
import android.view.View;


/**
 *
 * @author Juan Daniel Ornella
 */
public interface ListableFormatter {


    /**
     *
     * @param view target view to check
     * @return boolean indicating if the type of view is supported.
     */
    boolean supportedViewType(View view);

    /**
     *
     * @param value target value to check.
     * @return boolean indicating if the type of value is supported.
     */
    boolean supportedValueType(Object value);

    /**
     * Set the corresponding value when the value is null.
     * @param view taget view.
     */
    void setNullInView(View view);

    /**
     * Sets the value in the view if the view type and the value type are supported.
     *
     * @param value target value.
     * @param view target view.
     */
    void setValueInView(@NonNull View view, @NonNull Object value);



}
