# -*- coding: utf-8 -*-
#
# nest_gpu_code_generator_test.py
#
# This file is part of NEST.
#
# Copyright (C) 2004 The NEST Initiative
#
# NEST is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
#
# NEST is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with NEST.  If not, see <http://www.gnu.org/licenses/>.
import os
from pynestml.frontend.pynestml_frontend import generate_target


class TestNESTGPUCodeGenerator:
    """
    Tests code generation for the NEST GPU target.
    """

    def test_nest_gpu_code_generator(self):
        input_path = os.path.join(os.path.realpath(os.path.join(os.path.dirname(__file__), os.path.join(
            os.pardir, os.pardir, "models", "neurons", "iaf_psc_exp.nestml"))))
        target_path = "target_gpu"
        target_platform = "NEST_GPU"
        logging_level = "INFO"
        suffix = "_nestml"
        generate_target(input_path, target_platform, target_path,
                        logging_level=logging_level,
                        suffix=suffix)
