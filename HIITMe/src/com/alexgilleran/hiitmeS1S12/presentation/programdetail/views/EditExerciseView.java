/*
 * HIIT Me, a High Intensity Interval Training app for Android
 * Copyright (C) 2015 Alex Gilleran
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alexgilleran.hiitmeS1S12.presentation.programdetail.views;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;

import com.alexgilleran.hiitmeS1S12.R;
import com.alexgilleran.hiitmeS1S12.model.EffortLevel;
import com.alexgilleran.hiitmeS1S12.model.Exercise;
import com.alexgilleran.hiitmeS1S12.util.ViewUtils;

public class EditExerciseView extends TableLayout {
	private Spinner effortSpinner;
	private EditText durationMinutes;
	private EditText durationSeconds;
	private EditText name;

	private Exercise exercise;

	public EditExerciseView(Context context) {
		super(context);
	}

	public EditExerciseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		effortSpinner = (Spinner) findViewById(R.id.exercise_edit_effort_level);
		SpinnerAdapter effortAdapter = new EffortLevelAdapter(getContext());
		effortSpinner.setAdapter(effortAdapter);

		durationMinutes = (EditText) findViewById(R.id.exercise_edit_duration_minutes);
		durationSeconds = (EditText) findViewById(R.id.exercise_edit_duration_seconds);

		durationMinutes.setFilters(new InputFilter[]{new InputFilterMinMax("0", "99")});
		durationSeconds.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});

		name = (EditText) findViewById(R.id.exercise_edit_name);

		if (exercise != null) {
			render();
		}
	}

	public Exercise update() {
		exercise.setDuration(getDuration());
		exercise.setEffortLevel((EffortLevel) effortSpinner.getSelectedItem());
		exercise.setName(name.getText().toString());

		return exercise;
	}

	private int getDuration() {
		int totalDuration = 0;
		totalDuration += ViewUtils.getIntFromTextViewSafe(durationMinutes) * 60;
		totalDuration += ViewUtils.getIntFromTextViewSafe(durationSeconds);
		totalDuration = totalDuration * 1000;

		return totalDuration;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;

		render();
	}

	private void render() {
		if (effortSpinner != null) {
			effortSpinner.setSelection(exercise.getEffortLevel().ordinal());
		}

		if (durationMinutes != null) {
			durationMinutes.setText(String.format("%02d", exercise.getMinutes()));
		}
		if (durationSeconds != null) {
			durationSeconds.setText(String.format("%02d", exercise.getSeconds()));
		}

		if (name != null) {
			name.setText(exercise.getName());
		}
	}
}