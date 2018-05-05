

class Method:

    def __init__(self, id, name, class_name, is_final, is_static, access_modifier, parameter_types):
        self.id = id
        self.name = name
        self.class_name = class_name
        self.is_final = is_final
        self.is_static = is_static
        self.access_modifier = access_modifier
        self.parameter_types = parameter_types  # TODO: What will be the data type of this data type of this? Strings?
