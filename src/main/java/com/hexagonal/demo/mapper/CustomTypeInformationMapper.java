package com.hexagonal.demo.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.Alias;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomTypeInformationMapper implements TypeInformationMapper {
    private final String BASE_PACKAGE = "com.example.demo";
    private final Map<String, ClassTypeInformation> ALIAS_TYPE_MAP = new ConcurrentHashMap();
    private final Map<ClassTypeInformation, String> TYPE_ALIAS_MAP = new ConcurrentHashMap();

    public CustomTypeInformationMapper() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(CustomTypeAlias.class));
        Iterator var2 = scanner.findCandidateComponents(BASE_PACKAGE).iterator();

        while (var2.hasNext()) {
            BeanDefinition beanDefinition = (BeanDefinition)var2.next();

            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                ClassTypeInformation type = ClassTypeInformation.from(clazz);
                CustomTypeAlias wizCustomTypeAlias = clazz.getAnnotation(CustomTypeAlias.class);
                String aliasValue = wizCustomTypeAlias.value();
                this.ALIAS_TYPE_MAP.put(aliasValue, type);
                this.TYPE_ALIAS_MAP.put(type, aliasValue);
            } catch (ClassNotFoundException var8) {
                throw new IllegalStateException(String.format("Class [%s] could not be loaded.", beanDefinition.getBeanClassName()), var8);
            }
        }

    }

    public TypeInformation resolveTypeFrom(Alias alias) {
        String stringAlias = alias.mapTyped(String.class);
        return StringUtils.isBlank(stringAlias) ? null : this.ALIAS_TYPE_MAP.get(stringAlias);
    }

    public Alias createAliasFor(TypeInformation type) {
        ClassTypeInformation classType = (ClassTypeInformation)type;
        return this.TYPE_ALIAS_MAP.containsKey(classType) ? Alias.of(this.TYPE_ALIAS_MAP.get(type)) : Alias.NONE;
    }
}
