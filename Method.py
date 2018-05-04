

class Method:

    def __init__(self, name, class_name, is_final, is_static, access_modifier, parameter_types):
        self.id = self.create_method_id(class_name, name, parameter_types)  # method is identified by class, name, and parameter types
        self.name = name
        self.class_name = class_name
        self.is_final = is_final
        self.is_static = is_static
        self.access_modifier = access_modifier
        self.parameter_types = parameter_types  # TODO: What will be the data type of this data type of this? Strings?

    def create_method_id(self, class_name, name, parameter_types):
        method_id = class_name + '.' + name
        for parameter_type in parameter_types:
            method_id.append('.' + parameter_type)
        return method_id
