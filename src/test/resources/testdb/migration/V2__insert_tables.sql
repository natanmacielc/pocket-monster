INSERT INTO public.individuality(
                                 id,
                                 name,
                                 shiny,
                                 happiness,
                                 gender
                                 )
VALUES (
        1,
        'Charizard',
        true,
        255.0,
        'MALE'
        );

INSERT INTO public.stats(
                         id,
                         total
                         )
VALUES (
        1,
        1530
        );

INSERT INTO public.base_stat(
                             id,
                             stats_id,
                             category,
                             effort_value,
                             individual_value,
                             actual_value,
                             base_value,
                             stage_multiplier,
                             is_last_modified
                             )
VALUES (
        1,
        1,
        'HP',
        255,
        255,
        255,
        255,
        1,
        false
        );