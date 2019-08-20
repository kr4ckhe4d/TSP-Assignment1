package com.ec.selection;

import com.ec.Individual;
import com.ec.Population;

public interface Selection {
    Individual select(Population population);
}
