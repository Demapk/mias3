/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { HealthFacilityComponent } from '../../../../../../main/webapp/app/entities/health-facility/health-facility.component';
import { HealthFacilityService } from '../../../../../../main/webapp/app/entities/health-facility/health-facility.service';
import { HealthFacility } from '../../../../../../main/webapp/app/entities/health-facility/health-facility.model';

describe('Component Tests', () => {

    describe('HealthFacility Management Component', () => {
        let comp: HealthFacilityComponent;
        let fixture: ComponentFixture<HealthFacilityComponent>;
        let service: HealthFacilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [HealthFacilityComponent],
                providers: [
                    HealthFacilityService
                ]
            })
            .overrideTemplate(HealthFacilityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HealthFacilityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HealthFacilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HealthFacility(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.healthFacilities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
